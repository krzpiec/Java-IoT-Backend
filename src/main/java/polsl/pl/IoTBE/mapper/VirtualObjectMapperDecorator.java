package polsl.pl.IoTBE.mapper;

import org.apache.catalina.StoreManager;
import org.springframework.beans.factory.annotation.Autowired;
import polsl.pl.IoTBE.domain.VirtualTermometer;
import polsl.pl.IoTBE.repository.dao.Localization;
import polsl.pl.IoTBE.rest.dto.VirtualSensorInitDto;
import polsl.pl.IoTBE.storage.StorageMenager;

public abstract class VirtualObjectMapperDecorator implements VirtualObjectMapper {
    @Autowired
    private VirtualObjectMapper delegate;
    @Autowired
    private StorageMenager storageMenager;
    @Override
    public VirtualTermometer virtualSensorInitDtoToVirtualSensor(VirtualSensorInitDto virtualSensorInitDto){
//        VirtualTermometer virtualTermometer = delegate.virtualSensorInitDtoToVirtualSensor(virtualSensorInitDto);
//        Localization localization = new Localization();
//        localization.setLatitude(Double.parseDouble(virtualSensorInitDto.getLatitude()));
//        localization.setLongitude(Double.parseDouble(virtualSensorInitDto.getLongitude()));
//        virtualTermometer.setLocalization(localization);
//        String topicPrefix = virtualSensorInitDto.getMac() + "/" + virtualSensorInitDto.getChannelNumber();
//        virtualTermometer.setTopicPrefix(topicPrefix);
//
//
//        virtualTermometer.setVirtualChannel();

//        return virtualTermometer;

    }



}
