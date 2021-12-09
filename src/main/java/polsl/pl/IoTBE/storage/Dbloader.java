package polsl.pl.IoTBE.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import polsl.pl.IoTBE.domain.VirtualObject;
import polsl.pl.IoTBE.domain.VirtualTermometer;
import polsl.pl.IoTBE.repository.ChannelRepository;
import polsl.pl.IoTBE.repository.DeviceRepository;
import polsl.pl.IoTBE.repository.LocalizationRepository;
import polsl.pl.IoTBE.repository.TermometerRepository;
import polsl.pl.IoTBE.repository.dao.Channel;
import polsl.pl.IoTBE.repository.dao.Device;
import polsl.pl.IoTBE.repository.dao.Termometer;

import java.util.ArrayList;
import java.util.List;

@Component
public class Dbloader {
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    ChannelRepository channelRepository;
    @Autowired
    LocalizationRepository localizationRepository;
    @Autowired
    TermometerRepository termometerRepository;

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

    public List<VirtualObject> initializeVirtualObjectsFromDataBase(){

        List<Termometer> termometerList = termometerRepository.findAll();
        List<VirtualObject> virtualObjectList = new ArrayList<>();
        termometerList.forEach(sensor -> {
            virtualObjectList.add(
                    new VirtualTermometer(
                            sensor.getChannel().getDevice().getMacAdr(),
                            sensor.getChannel().getChannelNumber(),
                            null,
                            sensor.getLocalization(),
                            sensor.getUnit(),
                            0,
                            sensor.getChannel().getType()
                    )
            );

        });
        System.out.println("asdas");
        return virtualObjectList;
    }

}
