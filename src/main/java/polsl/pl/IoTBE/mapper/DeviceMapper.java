package polsl.pl.IoTBE.mapper;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import polsl.pl.IoTBE.repository.dao.Device;
import polsl.pl.IoTBE.rest.dto.DeviceDescriptionDto;
import polsl.pl.IoTBE.rest.dto.DeviceDto;

@Mapper(componentModel = "spring")
@DecoratedWith(DeviceMapperDecorator.class)
public interface DeviceMapper {

    DeviceDto deviceToDeviceDto(Device device);

    Device deviceDtoToDevice(DeviceDto deviceDto);

    DeviceDto deviceDescriptionDtoToDeviceDto(DeviceDescriptionDto deviceDescriptionDto);

    DeviceDescriptionDto deviceToDeviceDescriptionDto(Device device);
}
