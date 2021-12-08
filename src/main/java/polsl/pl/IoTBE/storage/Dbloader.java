package polsl.pl.IoTBE.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import polsl.pl.IoTBE.repository.ChannelRepository;
import polsl.pl.IoTBE.repository.DeviceRepository;
import polsl.pl.IoTBE.repository.LocalizationRepository;
import polsl.pl.IoTBE.repository.dao.Channel;
import polsl.pl.IoTBE.repository.dao.Device;

import java.util.List;

@Component
public class Dbloader {
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    ChannelRepository channelRepository;
    @Autowired
    LocalizationRepository localizationRepository;


    List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    Device getDeviceByMac(String mac){
        return deviceRepository.findByMacAdr(mac);
    }

    Device getDeviceById(Long id){
        return deviceRepository.findById(id).get();
    }

    List<Channel> getAllChannels(){
        return channelRepository.findAll();
    }

//    Channel getChannelByMacAndType(String mac, String type){
//        return channelRepository.findByMacAdrAndChannelNumber(mac,type);
//    }

    Channel getChannelByMacAndChannelNumber(String mac, Long channelNumber){
        Device device = deviceRepository.findByMacAdr(mac);
        return channelRepository.findByDeviceAndChannelNumber(device,channelNumber);
    }

}
