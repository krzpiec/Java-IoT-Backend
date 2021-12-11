package polsl.pl.IoTBE.rest;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import polsl.pl.IoTBE.exceptions.DevicePresentException;
import polsl.pl.IoTBE.mapper.DeviceMapper;
import polsl.pl.IoTBE.repository.dao.Device;
import polsl.pl.IoTBE.responseComminicates.DeviceDtoResponse;
import polsl.pl.IoTBE.rest.dto.DeviceDescriptionDto;
import polsl.pl.IoTBE.rest.dto.DeviceDto;
import polsl.pl.IoTBE.service.DeviceService;
import polsl.pl.IoTBE.validators.NewDeviceValidator;

@RestController
public class DeviceController {

@Autowired
    DeviceMapper deviceMapper;
@Autowired
    DeviceService deviceService;





    @PostMapping("/device/add")
    public ResponseEntity<DeviceDtoResponse> add(@RequestBody DeviceDescriptionDto deviceDescriptionDto) throws JSONException {


        Device device = deviceService.addDevice(deviceMapper.deviceDescriptionDtoToDevice(deviceDescriptionDto));

        DeviceDtoResponse deviceDtoResponse = new DeviceDtoResponse();
        deviceDtoResponse.setDeviceDto(deviceMapper.deviceToDeviceDto(device));
        deviceDtoResponse.setMesssage("Device created");


        return new ResponseEntity<DeviceDtoResponse>(deviceDtoResponse, HttpStatus.ACCEPTED);
    }

}
