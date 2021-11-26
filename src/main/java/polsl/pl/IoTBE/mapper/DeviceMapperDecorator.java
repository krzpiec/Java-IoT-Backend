package polsl.pl.IoTBE.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import polsl.pl.IoTBE.repository.dao.Device;
import polsl.pl.IoTBE.rest.dto.DeviceDescriptionDto;
import polsl.pl.IoTBE.rest.dto.DeviceDto;

public abstract class DeviceMapperDecorator implements DeviceMapper
{

    @Autowired
    private DeviceMapper delegate;

    @Autowired
    private ChannelMapper channelMapper;

    @Override
    public DeviceDto deviceToDeviceDto(Device device)
    {
       DeviceDto deviceDto = delegate.deviceToDeviceDto(device);
       DeviceDescriptionDto deviceDescriptionDto = delegate.deviceToDeviceDescriptionDto(device);
       deviceDto.setDeviceDescription(deviceDescriptionDto);
       addChannelList(deviceDto, device);

        return deviceDto;
    }

    @Override
    public DeviceDescriptionDto deviceToDeviceDescriptionDto(Device device){
        DeviceDescriptionDto deviceDescriptionDto = delegate.deviceToDeviceDescriptionDto(device);
        return deviceDescriptionDto;
    }

    @Override
    public Device deviceDtoToDevice(DeviceDto deviceDto)
    {
        Device device = delegate.deviceDtoToDevice(deviceDto);
        device.setChannels(null);
        return device;
    }

    @Override
    public DeviceDto deviceDescriptionDtoToDeviceDto(DeviceDescriptionDto deviceDescriptionDto)
    {
        DeviceDto deviceDto = delegate.deviceDescriptionDtoToDeviceDto(deviceDescriptionDto);
        deviceDto.setChannelDtoList(null);
        return deviceDto;
    }

    private DeviceDto addChannelList(DeviceDto deviceDto, Device device)
    {
        deviceDto.setChannelDtoList(null);
        return deviceDto;
    }
}
