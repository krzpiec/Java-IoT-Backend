package polsl.pl.IoTBE.domain;

import lombok.Data;
import lombok.Getter;
import polsl.pl.IoTBE.message.channel.VirtualChannel;
import polsl.pl.IoTBE.repository.dao.Channel;
import polsl.pl.IoTBE.repository.dao.Localization;

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

    protected String topicPrefix;//format MAC/channel/
    protected String mac;
    protected long channelNumber;
    protected VirtualChannel virtualChannel;
    protected Localization localization;
    protected String desiredType;
}
