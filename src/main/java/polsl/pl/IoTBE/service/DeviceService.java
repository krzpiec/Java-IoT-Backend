package polsl.pl.IoTBE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polsl.pl.IoTBE.repository.DeviceRepository;
import polsl.pl.IoTBE.repository.dao.Device;
import polsl.pl.IoTBE.rest.dto.DeviceDescriptionDto;
import polsl.pl.IoTBE.rest.dto.DeviceDto;

import java.sql.Timestamp;

@Service
public class DeviceService {

    @Autowired
    DeviceRepository deviceRepository;

    public Device addDevice(DeviceDescriptionDto deviceDto)
    {

        Device device = new Device();
        device.setMacAdr(deviceDto.getMacAdr());
        device.setDescription(deviceDto.getDescription());
        device.setFriendlyName(deviceDto.getFriendlyName());
        device.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return device;
    }


}
