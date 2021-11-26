package polsl.pl.IoTBE.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import polsl.pl.IoTBE.repository.*;
import polsl.pl.IoTBE.repository.dao.*;

import javax.xml.stream.Location;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private TermometerRepository termometerRepository;
    @Autowired
    private TemperatureHistoryRepository temperatureHistoryRepository;
    @Autowired
    private LocalizationRepository localizationRepository;

    @GetMapping("/toja")
    public ResponseEntity<String> odp(){
//        TestEntity test = new TestEntity();
//        test.setName("stefan");

        Localization localization = new Localization();
        localization.setDescription("HEHE");
        localization.setLatitude(1);
        localization.setLongitude(1);
        localizationRepository.save(localization);
        List<TemperatureHistory> temperatureHistories = new ArrayList<TemperatureHistory>();


        Termometer termometer = new Termometer();
        termometer.setLocalization(localization);
        termometer.setUnit("twojstary*N/69stopniCelsjusza");
        termometer.setTemperatureHistories(temperatureHistories);
        termometerRepository.save(termometer);

        TemperatureHistory temperatureHistory0 = new TemperatureHistory();
        temperatureHistory0.setValue(1);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        temperatureHistory0.setMeasureTime(timestamp);
        temperatureHistory0.setTermometer(termometer);
        temperatureHistoryRepository.save(temperatureHistory0);


        TemperatureHistory temperatureHistory1 = new TemperatureHistory();
        temperatureHistory1.setValue(2);
        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());
        temperatureHistory1.setMeasureTime(timestamp1);
        temperatureHistory1.setTermometer(termometer);
        temperatureHistoryRepository.save(temperatureHistory1);

//        testRepository.save(test);

        return ResponseEntity.ok("JD");
    }

}
