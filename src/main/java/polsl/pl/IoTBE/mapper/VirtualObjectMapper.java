package polsl.pl.IoTBE.mapper;


import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import polsl.pl.IoTBE.domain.VirtualObject;
import polsl.pl.IoTBE.domain.VirtualTermometer;
import polsl.pl.IoTBE.rest.dto.VirtualObjectDto;
import polsl.pl.IoTBE.rest.dto.VirtualSensorDto;
import polsl.pl.IoTBE.rest.dto.VirtualSensorInitDto;

@Mapper(componentModel = "spring")
@DecoratedWith(VirtualObjectMapperDecorator.class)
public interface VirtualObjectMapper {

    VirtualObjectDto virtualObjectToVirtualObjectDto(VirtualObject virtualObject);

    VirtualTermometer virtualSensorInitDtoToVirtualSensor(VirtualSensorInitDto virtualSensorInitDto);

    VirtualSensorDto virtualSensorToVirtualSensorDto(VirtualTermometer virtualTermometer);

}
