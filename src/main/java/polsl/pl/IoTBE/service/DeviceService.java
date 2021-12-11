package polsl.pl.IoTBE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import polsl.pl.IoTBE.common.MqttConfigValues;
import polsl.pl.IoTBE.exceptions.DevicePresentException;
import polsl.pl.IoTBE.exceptions.InvalidConfigException;
import polsl.pl.IoTBE.exceptions.InvalidMacException;
import polsl.pl.IoTBE.exceptions.TopicAlreadySubscribedException;
import polsl.pl.IoTBE.mapper.DeviceMapper;
import polsl.pl.IoTBE.mqtt.ConfigHandler;
import polsl.pl.IoTBE.mqtt.MqttController;
import polsl.pl.IoTBE.repository.ChannelRepository;
import polsl.pl.IoTBE.repository.DeviceRepository;
import polsl.pl.IoTBE.repository.dao.Channel;
import polsl.pl.IoTBE.repository.dao.Device;
import polsl.pl.IoTBE.storage.StorageMenager;
import polsl.pl.IoTBE.validators.NewDeviceValidator;

import java.util.List;

@Service
public class DeviceService {

    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    ChannelRepository channelRepository;
    @Autowired
    StorageMenager storageMenager;
    @Autowired
    ConfigHandler configHandler;
    @Autowired
    NewDeviceValidator newDeviceValidator;
    @Autowired
    DeviceMapper deviceMapper;
    @Autowired
    MqttController mqttController;


    public Device addDevice(Device device) throws JSONException {

        if(!newDeviceValidator.validateMac(device.getMacAdr()))
            throw new InvalidMacException(device.getMacAdr());

        Device devicePresent = newDeviceValidator.isDevicePresent(device);
        if( devicePresent != null) {
            throw new DevicePresentException(deviceMapper.deviceToDeviceDto(devicePresent));
        }


        boolean topicAlreadySubscribed = this.configHandler.subscribeToGetConfigTopic(device);
        if(topicAlreadySubscribed)
            throw new TopicAlreadySubscribedException(device.getMacAdr());
        String deviceConfigTopic = device.getMacAdr() + MqttConfigValues.configSuffix + MqttConfigValues.sendRequestSuffix;
        //todo fix this
      //  this.mqttController.publish("00:00:00:00:00:04/config/get", MqttConfigValues.configMessageGet);

        JSONObject deviceConfigJson = configHandler.getConfig();
        String jsonValidationResult = newDeviceValidator.validatateJsonConfig(deviceConfigJson);
        if(!jsonValidationResult.equals("OK")){
            this.configHandler.removeTopic(deviceConfigTopic);
            throw new InvalidConfigException(jsonValidationResult);
        }

        List<Channel> channelList = this.configHandler.getChannelListFromConfigJson(device);

        storageMenager.addDevice(device);
        storageMenager.addChannelsFromChannelList(channelList);
        storageMenager.addVirtualChannelsFromChannelList(channelList);

        deviceRepository.save(device);

        channelList.forEach(channel -> {
            channel.setDevice(device);
            channelRepository.save(channel);});

        device.setChannels(channelList);

        return device;
    }




}
