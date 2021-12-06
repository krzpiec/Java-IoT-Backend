package polsl.pl.IoTBE.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import polsl.pl.IoTBE.repository.dao.Device;
import polsl.pl.IoTBE.rest.dto.*;
import polsl.pl.IoTBE.service.VirtualObjectService;

public class VirtualObjectController {

    @Autowired
    VirtualObjectService virtualObjectService;

    @PostMapping("/virtualObject/add")
    public ResponseEntity<String> check(@RequestBody VirtualObjectInitDto virtualObjectInitDto) throws JSONException {


       String response = "";
      boolean appereance = virtualObjectService.checkIfExists(virtualObjectInitDto.getMac(), virtualObjectInitDto.getChannelNumber());
      if(appereance)
          return ResponseEntity.ok("Channel taken");

        return ResponseEntity.ok("Channel free");
    }


    @PostMapping("/virtualObject/add/Sensor")
    public ResponseEntity<VirtualSensorDto> add(@RequestBody VirtualSensorInitDto virtualSensorInitDto) throws JSONException {



        return ResponseEntity.ok("Channel free");
    }
}
