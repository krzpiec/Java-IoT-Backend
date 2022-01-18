package polsl.pl.IoTBE.rest;

import com.github.davidmoten.rtree.geometry.Geometries;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import polsl.pl.IoTBE.RTree.TreeTypes;
import polsl.pl.IoTBE.domain.VirtualObject;
import polsl.pl.IoTBE.domain.VirtualTermometer;
import polsl.pl.IoTBE.mapper.VirtualObjectMapper;
import polsl.pl.IoTBE.message.channel.TempSensorChannel;
import polsl.pl.IoTBE.message.channel.VirtualChannel;
import polsl.pl.IoTBE.repository.ChannelRepository;
import polsl.pl.IoTBE.repository.DeviceRepository;
import polsl.pl.IoTBE.repository.LocalizationRepository;
import polsl.pl.IoTBE.repository.TermometerRepository;
import polsl.pl.IoTBE.repository.dao.*;
import polsl.pl.IoTBE.responseComminicates.MessageResponse;
import polsl.pl.IoTBE.rest.dto.*;
import polsl.pl.IoTBE.service.VirtualObjectService;
import polsl.pl.IoTBE.storage.StorageMenager;

import java.util.ArrayList;
import java.util.List;

@RestController
public class VirtualObjectController {

    @Autowired
    VirtualObjectService virtualObjectService;
    @Autowired
    VirtualObjectMapper virtualObjectMapper;
    @Autowired
    StorageMenager storageMenager;
    @Autowired
    LocalizationRepository localizationRepository;
    @Autowired
    TermometerRepository termometerRepository;
    @Autowired
    ChannelRepository channelRepository;
    @Autowired
    DeviceRepository deviceRepository;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/virtualDevices/get/{macAdr}/{channelNumber}")
    public ResponseEntity<VirtualObjectDto> getVirtualObjectByMacAndChannelNumber(@PathVariable("macAdr") String macAdr, @PathVariable("channelNumber") long channelNumber){
        VirtualObjectDto virtualObjectDto = this.virtualObjectMapper.virtualObjectToVirtualObjectDto(this.storageMenager.getVirtualObjectByMacAndChannelNumber(macAdr,channelNumber));
        return new ResponseEntity<>(virtualObjectDto, HttpStatus.OK);
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/virtualDevices/getall")
    public ResponseEntity<List<VirtualObjectDto>> getAllVirtualDevices(){
        List<VirtualObjectDto> virtualObjectDtoList = new ArrayList<>();
        this.storageMenager.getVirtualObjectList().forEach(
                vo ->{
                    VirtualObjectDto virtualObjectDto = this.virtualObjectMapper.virtualObjectToVirtualObjectDto(vo);
                    virtualObjectDtoList.add(virtualObjectDto);
                }
        );

        return new ResponseEntity<>(virtualObjectDtoList, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/virtualDevices/add")
    public ResponseEntity<MessageResponse> check(@RequestBody VirtualObjectInitDto virtualObjectInitDto) {
       virtualObjectService.checkPassedMacAndChannelNumber(virtualObjectInitDto.getMacAdr(), virtualObjectInitDto.getChannelNumber(), virtualObjectInitDto.getType());
       return new ResponseEntity<>(new MessageResponse("channel free"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/virtualDevices/edit")
    public ResponseEntity<MessageResponse> editVirtualObject(@RequestBody EditVirtualObjectDto editVirtualObjectDto) {

        System.out.println(editVirtualObjectDto.getLatitude());
        Device device = deviceRepository.findByMacAdr(editVirtualObjectDto.getMac());

        Channel channel = channelRepository.findByDeviceAndChannelNumber(device, editVirtualObjectDto.getChannelNumber());

        Termometer termometer = termometerRepository.findTermometerByChannel(channel);

        termometer.setDescription(editVirtualObjectDto.getDescription());
        Localization localization = new Localization();
        localization.setDescription(editVirtualObjectDto.getLocalizationDescription());
        localization.setLatitude(editVirtualObjectDto.getLatitude());
        localization.setLongitude(editVirtualObjectDto.getLongitude());
        localizationRepository.save(localization);
        termometer.setLocalization(localization);
        termometerRepository.save(termometer);

        VirtualObject vo = this.storageMenager.getVirtualObjectByMacAndChannelNumber(editVirtualObjectDto.getMac(), editVirtualObjectDto.getChannelNumber());

        vo.setLocalization(localization);

        this.storageMenager.recunstructRTree();


        return new ResponseEntity<>(new MessageResponse("Object edited"), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/virtualDevices/add/Sensor")
    public ResponseEntity<VirtualSensorDto> add(@RequestBody VirtualSensorInitDto virtualSensorInitDto) throws JSONException {

        VirtualTermometer virtualTermometer = virtualObjectMapper.virtualSensorInitDtoToVirtualSensor(virtualSensorInitDto);
        storageMenager.addVirtualObject(virtualTermometer);
        localizationRepository.save(virtualTermometer.getLocalization());


        List<TemperatureHistory> temperatureHistoriesTermometer0 = new ArrayList<>();
        Termometer termometer0 = new Termometer();
        termometer0.setTemperatureHistories(temperatureHistoriesTermometer0);
        termometer0.setDescription(virtualSensorInitDto.getDescription());
        termometer0.setUnit(virtualSensorInitDto.getUnit());
        termometer0.setLocalization(virtualTermometer.getLocalization());
        termometer0.setChannel(storageMenager.getChannelByMacAndChannelNumber(virtualSensorInitDto.getMacAdr(), Long.parseLong(virtualSensorInitDto.getChannelNumber())));
        termometerRepository.save(termometer0);

        VirtualSensorDto virtualSensorDto = virtualObjectMapper.virtualSensorToVirtualSensorDto(virtualTermometer);


        return new ResponseEntity<>(virtualSensorDto, HttpStatus.OK);

    }

}