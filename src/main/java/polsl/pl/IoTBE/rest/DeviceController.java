package polsl.pl.IoTBE.rest;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<DeviceDto> add(@RequestBody DeviceDescriptionDto deviceDescriptionDto) throws JSONException {


        Device device = deviceService.addDevice(deviceMapper.deviceDescriptionDtoToDevice(deviceDescriptionDto));


        return ResponseEntity.ok(deviceMapper.deviceToDeviceDto(device));
    }

}
