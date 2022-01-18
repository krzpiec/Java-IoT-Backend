package polsl.pl.IoTBE.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import polsl.pl.IoTBE.events.MeasurementDonePublisher;
import polsl.pl.IoTBE.message.channel.TempSensor;
import polsl.pl.IoTBE.message.channel.VirtualChannel;
import polsl.pl.IoTBE.repository.ChannelRepository;
import polsl.pl.IoTBE.repository.DeviceRepository;
import polsl.pl.IoTBE.repository.TemperatureHistoryRepository;
import polsl.pl.IoTBE.repository.TermometerRepository;
import polsl.pl.IoTBE.repository.dao.*;

import java.sql.Timestamp;

@Getter
@Setter
public class VirtualTermometer extends VirtualObject implements TempSensor {


    @Autowired
    TemperatureHistoryRepository temperatureHistoryRepository;
    @Autowired
    TermometerRepository termometerRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    ChannelRepository channelRepository;

    public VirtualTermometer(String mac, long channelNumber, VirtualChannel virtualChannel, Localization localization, String unit, int lastReadValue, String desiredType) {
        super(mac, channelNumber, virtualChannel, localization, desiredType);
        this.unit = unit;
        this.lastReadValue = lastReadValue;
    }

    private String unit;
    private double lastReadValue; //zmienic na double


    @Override
    public double getValue(){
        return this.lastReadValue;
    }


    @Override
    public void changeTemperature(double delta)
    {
        this.lastValueTimestamp = new Timestamp(System.currentTimeMillis());

       this.lastReadValue = delta;

//todo save reading nie dziala
       // this.saveReading(delta);
    }

    private void saveReading(double delta){
        Device device = deviceRepository.findByMacAdr(this.mac);
        Channel channel = channelRepository.findByDeviceAndChannelNumber(device, this.channelNumber);
        Termometer termometer = termometerRepository.findTermometerByChannel(channel);

        TemperatureHistory temperatureHistory = new TemperatureHistory();
        temperatureHistory.setTermometer(termometer);
        temperatureHistory.setMeasureTime(this.lastValueTimestamp);
        //todo int na doubla dac
        temperatureHistory.setValue((int)this.lastReadValue);

        temperatureHistoryRepository.save(temperatureHistory);


    }
}
