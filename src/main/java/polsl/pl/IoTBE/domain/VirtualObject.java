package polsl.pl.IoTBE.domain;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import polsl.pl.IoTBE.events.MeasurementDonePublisher;
import polsl.pl.IoTBE.message.channel.VirtualChannel;
import polsl.pl.IoTBE.repository.dao.Channel;
import polsl.pl.IoTBE.repository.dao.Localization;

import java.sql.Timestamp;

@Data
public abstract class VirtualObject {




    public VirtualObject(String mac, long channelNumber, VirtualChannel virtualChannel, Localization localization,String desiredType) {

        this.mac = mac;
        this.channelNumber = channelNumber;
        this.topicPrefix = mac + "/" + Long.toString(channelNumber);
        this.virtualChannel = virtualChannel;
        this.localization = localization;
        this.desiredType = desiredType;
    }

    public abstract double getValue();

    //todo kiedys timestamp dodania
    protected Timestamp lastValueTimestamp;
    protected String topicPrefix;//format MAC/channel/
    protected String mac;
    protected long channelNumber;
    protected VirtualChannel virtualChannel;
    protected Localization localization;
    protected String desiredType;
}
