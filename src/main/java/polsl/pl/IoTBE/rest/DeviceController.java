package polsl.pl.IoTBE.rest;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import polsl.pl.IoTBE.exceptions.DevicePresentException;
import polsl.pl.IoTBE.mapper.DeviceMapper;
import polsl.pl.IoTBE.repository.dao.Device;
import polsl.pl.IoTBE.responseComminicates.DeviceDtoResponse;
import polsl.pl.IoTBE.rest.dto.DeviceDescriptionDto;
import polsl.pl.IoTBE.rest.dto.DeviceDto;
import polsl.pl.IoTBE.service.DeviceService;
import polsl.pl.IoTBE.storage.StorageMenager;
import polsl.pl.IoTBE.validators.NewDeviceValidator;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DeviceController {

    @Autowired
    StorageMenager storageMenager;
@Autowired
    DeviceMapper deviceMapper;
@Autowired
    DeviceService deviceService;


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/device/get/{macAdr}")
    public ResponseEntity<DeviceDto> getDeviceByMac(@PathVariable("macAdr") String macAdr){
        DeviceDto deviceDto = this.deviceMapper.deviceToDeviceDto(this.storageMenager.getDeviceByMac(macAdr));
        return new ResponseEntity<>(deviceDto, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/device/getall")
    public ResponseEntity<List<DeviceDto>> getAllDeviceAsDeviceDtoList(){
    List<DeviceDto> deviceDtoList = new ArrayList<>();
    this.storageMenager.getDeviceList().forEach(device -> {
        DeviceDto deviceDto = deviceMapper.deviceToDeviceDto(device);
        deviceDtoList.add(deviceDto);
    });
    return new ResponseEntity<>(deviceDtoList, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/device/add")
    public ResponseEntity<DeviceDtoResponse> add(@RequestBody DeviceDescriptionDto deviceDescriptionDto) throws JSONException {

        System.out.println(deviceDescriptionDto);
        Device device = deviceService.addDevice(deviceMapper.deviceDescriptionDtoToDevice(deviceDescriptionDto));

        DeviceDtoResponse deviceDtoResponse = new DeviceDtoResponse();
        deviceDtoResponse.setDeviceDto(deviceMapper.deviceToDeviceDto(device));
        deviceDtoResponse.setMesssage("Device created");


        return new ResponseEntity<DeviceDtoResponse>(deviceDtoResponse, HttpStatus.OK);
    }

}
