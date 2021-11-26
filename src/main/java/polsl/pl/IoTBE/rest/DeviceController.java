package polsl.pl.IoTBE.rest;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import polsl.pl.IoTBE.mapper.DeviceMapper;
import polsl.pl.IoTBE.repository.dao.Device;
import polsl.pl.IoTBE.rest.dto.DeviceDescriptionDto;
import polsl.pl.IoTBE.rest.dto.DeviceDto;
import polsl.pl.IoTBE.service.DeviceService;

@RestController
public class DeviceController {

@Autowired
    DeviceMapper deviceMapper;
@Autowired
    DeviceService deviceService;



    @PostMapping("/device/add")
    //@Body @Valid
    public ResponseEntity<DeviceDto> add(@RequestBody DeviceDescriptionDto deviceDescriptionDto) {

        Device device = deviceService.addDevice(deviceDescriptionDto);


//        DeviceDto deviceDto = new DeviceDto();
//
//        DeviceDescriptionDto descriptionDto = new DeviceDescriptionDto();
//        descriptionDto.setDescription(device.getDescription());
//        descriptionDto.setFriendlyName(device.getFriendlyName());
//        descriptionDto.setMacAdr(device.getMacAdr());
//
//        deviceDto.setDeviceDescription(descriptionDto);
//        deviceDto.setCreateTime(device.getCreateTime());

        return ResponseEntity.ok(deviceMapper.deviceToDeviceDto(device));
    }

}
