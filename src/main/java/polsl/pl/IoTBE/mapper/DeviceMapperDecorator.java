package polsl.pl.IoTBE.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import polsl.pl.IoTBE.repository.dao.Device;
import polsl.pl.IoTBE.responseComminicates.DeviceDtoResponse;
import polsl.pl.IoTBE.rest.dto.ChannelDto;
import polsl.pl.IoTBE.rest.dto.DeviceDescriptionDto;
import polsl.pl.IoTBE.rest.dto.DeviceDto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public abstract class DeviceMapperDecorator implements DeviceMapper
{

    @Autowired
    private DeviceMapper delegate;

    @Autowired
    private ChannelMapper channelMapper;

    @Override
    public DeviceDto deviceToDeviceDto(Device device) {
       DeviceDto deviceDto = delegate.deviceToDeviceDto(device);
       DeviceDescriptionDto deviceDescriptionDto = delegate.deviceToDeviceDescriptionDto(device);
       List<ChannelDto> channelDtoList = new ArrayList<>();
       device.getChannels().forEach(channel ->{
           ChannelDto channelDto = channelMapper.channelToChannelDto(channel);
           channelDtoList.add(channelDto);
       });
       deviceDto.setDeviceDescription(deviceDescriptionDto);
       deviceDto.setChannelDtoList(channelDtoList);

        return deviceDto;
    }

    @Override
    public DeviceDescriptionDto deviceToDeviceDescriptionDto(Device device){
        DeviceDescriptionDto deviceDescriptionDto = delegate.deviceToDeviceDescriptionDto(device);
        return deviceDescriptionDto;
    }

    @Override
    public Device deviceDtoToDevice(DeviceDto deviceDto) {
        Device device = delegate.deviceDtoToDevice(deviceDto);
        device.setChannels(null);
        return device;
    }

    @Override
    public Device deviceDescriptionDtoToDevice(DeviceDescriptionDto deviceDescriptionDto){
        Device device = new Device();

        device.setMacAdr(deviceDescriptionDto.getMacAdr());
        device.setDescription(deviceDescriptionDto.getDescription());
        device.setFriendlyName(deviceDescriptionDto.getFriendlyName());
        device.setCreateTime(new Timestamp(System.currentTimeMillis()));
        device.setChannels(null);
        return device;
    }

    @Override
    public DeviceDto deviceDescriptionDtoToDeviceDto(DeviceDescriptionDto deviceDescriptionDto) {
        DeviceDto deviceDto = delegate.deviceDescriptionDtoToDeviceDto(deviceDescriptionDto);
        deviceDto.setChannelDtoList(null);
        return deviceDto;
    }



}
