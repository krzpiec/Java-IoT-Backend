package polsl.pl.IoTBE.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import polsl.pl.IoTBE.events.MeasurementDonePublisher;
import polsl.pl.IoTBE.message.channel.TempSensor;
import polsl.pl.IoTBE.message.channel.VirtualChannel;
import polsl.pl.IoTBE.repository.dao.Localization;

import java.sql.Timestamp;

@Getter
@Setter
public class VirtualTermometer extends VirtualObject implements TempSensor {



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
    }
}
