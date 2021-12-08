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
import java.util.Optional;

@RestController
public class Controller {

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private TermometerRepository termometerRepository;
    @Autowired
    private TemperatureHistoryRepository temperatureHistoryRepository;
    @Autowired
    private LocalizationRepository localizationRepository;

    @GetMapping("/toja")
    public ResponseEntity<String> odp(){



        //CREATE DEVICE AND CHANNELS

        //create channel list
        List<Channel> channelListDevice0 = new ArrayList<>();

        //create and save device
        Device device0 = testCreateDevice("00:0A:E6:3E:FD:E1","Device created for testing",
                "testDevice", channelListDevice0);
        deviceRepository.save(device0);


        //fill channelList
        Channel channel0 = createTestChannel(0, "Sensor");
        channelRepository.save(channel0);
        Channel channel1 = createTestChannel(1, "Bo");
        channelRepository.save(channel1);
        Channel channel2 = createTestChannel(2, "BOPWM" );
        channelRepository.save(channel2);

        channelListDevice0.add(channel0);
        channelListDevice0.add(channel1);
        channelListDevice0.add(channel2);


        //add device
        channelListDevice0.forEach(channel -> {
            channel.setDevice(device0);

        });

        //Device and channel end


        //CREATE TERMOMETER

        Localization localization = testCreateLocalization();
        localizationRepository.save(localization);

        List<TemperatureHistory> temperatureHistoriesTermometer0 = new ArrayList<>();


        Termometer termometer0 = createTestTermometer(channel0, localization, temperatureHistoriesTermometer0);

        termometerRepository.save(termometer0);

        TemperatureHistory temperatureHistory0 = createTestTemperatureHistory(0);
        temperatureHistoryRepository.save(temperatureHistory0);
        TemperatureHistory temperatureHistory1 = createTestTemperatureHistory(1);
        temperatureHistoryRepository.save(temperatureHistory1);
        TemperatureHistory temperatureHistory2 = createTestTemperatureHistory(2);
        temperatureHistoryRepository.save(temperatureHistory2);
        TemperatureHistory temperatureHistory3 = createTestTemperatureHistory(3);
        temperatureHistoryRepository.save(temperatureHistory3);

        temperatureHistoriesTermometer0.add(temperatureHistory0);
        temperatureHistoriesTermometer0.add(temperatureHistory1);
        temperatureHistoriesTermometer0.add(temperatureHistory2);
        temperatureHistoriesTermometer0.add(temperatureHistory3);


        temperatureHistoriesTermometer0.forEach(temperatureHistory -> {
            temperatureHistory.setTermometer(termometer0);
        });

        return ResponseEntity.ok("Tworzenie testowe zakonczone pomyslnie :))");
    }


    private Device testCreateDevice(String macAdr, String description, String friendlyName, List<Channel> channelList){
        Device device = new Device();
        device.setMacAdr(macAdr);
        device.setMacAdr("00:0A:E6:3E:FD:E1");
        device.setCreateTime(new Timestamp(System.currentTimeMillis()));
        device.setDescription("Device created for testing");
        device.setFriendlyName("testDevice");
        device.setChannels(channelList);
        return device;
    }

    private Channel createTestChannel(int channelNumber, String type){
        Channel channel = new Channel();
        channel.setChannelNumber(channelNumber);
        channel.setType(type);
        channel.setDevice(null);
        return channel;
    }

    private Localization testCreateLocalization(){
        Localization localization = new Localization();
        localization.setDescription("testLocalization");
        localization.setLatitude(1);
        localization.setLongitude(1);
        return localization;
    }

    private Termometer createTestTermometer(Channel channel, Localization localization, List<TemperatureHistory> temperatureHistories){
        Termometer termometer = new Termometer();
        termometer.setLocalization(localization);
        termometer.setUnit("Celsius");
        termometer.setTemperatureHistories(temperatureHistories);
        termometer.setDescription("testTermometer");
        termometer.setChannel(channel);
        return termometer;
    }

    private TemperatureHistory createTestTemperatureHistory(int value){
        TemperatureHistory temperatureHistory = new TemperatureHistory();
        temperatureHistory.setMeasureTime(new Timestamp(System.currentTimeMillis()));
        temperatureHistory.setValue(value);
        return temperatureHistory;
    }
}
