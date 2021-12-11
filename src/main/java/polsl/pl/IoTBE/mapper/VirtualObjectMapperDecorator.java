package polsl.pl.IoTBE.mapper;

import org.apache.catalina.StoreManager;
import org.springframework.beans.factory.annotation.Autowired;
import polsl.pl.IoTBE.domain.VirtualTermometer;
import polsl.pl.IoTBE.repository.dao.Device;
import polsl.pl.IoTBE.repository.dao.Localization;
import polsl.pl.IoTBE.responseComminicates.DeviceDtoResponse;
import polsl.pl.IoTBE.rest.dto.LocalizationDto;
import polsl.pl.IoTBE.rest.dto.VirtualSensorDto;
import polsl.pl.IoTBE.rest.dto.VirtualSensorInitDto;
import polsl.pl.IoTBE.storage.StorageMenager;

public abstract class VirtualObjectMapperDecorator implements VirtualObjectMapper {
    @Autowired
    private VirtualObjectMapper delegate;
    @Autowired
    private StorageMenager storageMenager;
    @Override
    public VirtualTermometer virtualSensorInitDtoToVirtualSensor(VirtualSensorInitDto virtualSensorInitDto){
        VirtualTermometer virtualTermometer = delegate.virtualSensorInitDtoToVirtualSensor(virtualSensorInitDto);
        Localization localization = new Localization();
        localization.setLatitude(Double.parseDouble(virtualSensorInitDto.getLatitude()));
        localization.setLongitude(Double.parseDouble(virtualSensorInitDto.getLongitude()));
        virtualTermometer.setLocalization(localization);
        String topicPrefix = virtualSensorInitDto.getMacAdr() + "/" + virtualSensorInitDto.getChannelNumber();
        virtualTermometer.setTopicPrefix(topicPrefix);
        virtualTermometer.setMac(virtualSensorInitDto.getMacAdr());
        virtualTermometer.setDesiredType(
                storageMenager.getTypeByMacAndChannelNumber(virtualSensorInitDto.getMacAdr(),
                        Long.parseLong(virtualSensorInitDto.getChannelNumber())));

        virtualTermometer.setVirtualChannel(storageMenager.getVirtualChannelByType("Sensor"));

        return virtualTermometer;

    }

    //delegate
    @Override
    public VirtualSensorDto virtualSensorToVirtualSensorDto(VirtualTermometer virtualTermometer){
        VirtualSensorDto virtualSensorDto = delegate.virtualSensorToVirtualSensorDto(virtualTermometer);
        virtualSensorDto.setDescription("test");
        LocalizationDto localizationDto = new LocalizationDto(virtualTermometer.getLocalization(), "lokalizacja");

        virtualSensorDto.setLocalizationDto(localizationDto);
        virtualSensorDto.setUnit(virtualTermometer.getUnit());
        virtualSensorDto.setChannelNumber(Long.toString(virtualTermometer.getChannelNumber()));
        virtualSensorDto.setMac(virtualTermometer.getMac());
        virtualSensorDto.setVirtualChannelType(virtualTermometer.getDesiredType());
        return virtualSensorDto;
    }



}
