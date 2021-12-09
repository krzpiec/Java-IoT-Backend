package polsl.pl.IoTBE.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import polsl.pl.IoTBE.domain.VirtualTermometer;
import polsl.pl.IoTBE.mapper.VirtualObjectMapper;
import polsl.pl.IoTBE.message.channel.TempSensorChannel;
import polsl.pl.IoTBE.message.channel.VirtualChannel;
import polsl.pl.IoTBE.repository.LocalizationRepository;
import polsl.pl.IoTBE.repository.TermometerRepository;
import polsl.pl.IoTBE.repository.dao.Device;
import polsl.pl.IoTBE.repository.dao.Localization;
import polsl.pl.IoTBE.repository.dao.TemperatureHistory;
import polsl.pl.IoTBE.repository.dao.Termometer;
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

    @PostMapping("/virtualObject/add")
    public ResponseEntity<String> check(@RequestBody VirtualObjectInitDto virtualObjectInitDto) throws JSONException {


        String response = "";
        boolean channelTaken = virtualObjectService.checkIfExists(virtualObjectInitDto.getMac(), virtualObjectInitDto.getChannelNumber());
        if (channelTaken)
            return ResponseEntity.ok("Channel taken");

        return ResponseEntity.ok("Channel free");
    }


    @PostMapping("/virtualObject/add/Sensor")
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


        return ResponseEntity.ok(virtualSensorDto);

    }

}