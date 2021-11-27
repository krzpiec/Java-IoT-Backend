package polsl.pl.IoTBE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polsl.pl.IoTBE.mapper.DeviceMapper;
import polsl.pl.IoTBE.mqtt.MqttSubscriberConfig;
import polsl.pl.IoTBE.mqtt.MqttSubscriver;
import polsl.pl.IoTBE.repository.DeviceRepository;
import polsl.pl.IoTBE.repository.dao.Device;
import polsl.pl.IoTBE.rest.dto.DeviceDescriptionDto;
import polsl.pl.IoTBE.rest.dto.DeviceDto;
import polsl.pl.IoTBE.storage.StorageMenager;

import java.sql.Timestamp;

@Service
public class DeviceService {

    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    DeviceMapper deviceMapper;
    @Autowired
    StorageMenager storageMenager;
    @Autowired
    MqttSubscriver mqttSubscriver;

    public Device addDevice(Device device)
    {

        mqttSubscriver.addTopic("asd");


      deviceRepository.save(device);

        return device;
    }





}
