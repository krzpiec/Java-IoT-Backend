package polsl.pl.IoTBE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import polsl.pl.IoTBE.mqtt.ConfigHandler;
import polsl.pl.IoTBE.repository.ChannelRepository;
import polsl.pl.IoTBE.repository.DeviceRepository;
import polsl.pl.IoTBE.repository.dao.Channel;
import polsl.pl.IoTBE.repository.dao.Device;
import polsl.pl.IoTBE.storage.StorageMenager;

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


//    //testtutaj
//    @GetMapping("/getDevice")
//    public void getDevices(){
//        List<Device> deviceList = deviceRepository.findAll();
//        System.out.println(deviceList);
//    }


    public Device addDevice(Device device) throws JSONException {

        if(storageMenager.isDevicePresent(device)) {
            return device;
        }

        this.configHandler.addConfigGetTopic(device);

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
