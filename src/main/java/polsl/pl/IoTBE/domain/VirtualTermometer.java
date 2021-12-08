package polsl.pl.IoTBE.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import polsl.pl.IoTBE.message.channel.TempSensor;
import polsl.pl.IoTBE.message.channel.VirtualChannel;
import polsl.pl.IoTBE.repository.dao.Localization;

@Getter
@Setter
public class VirtualTermometer extends VirtualObject implements TempSensor {


    public VirtualTermometer(String mac, long channelNumber, VirtualChannel virtualChannel, Localization localization, String unit, int lastReadValue, String desiredType) {
        super(mac, channelNumber, virtualChannel, localization, desiredType);
        this.unit = unit;
        this.lastReadValue = lastReadValue;
    }

    private String unit;
    private int lastReadValue; //zmienic na double


    @Override
    public void changeTemperature(int delta)
    {
        this.lastReadValue = delta;

    }
}
