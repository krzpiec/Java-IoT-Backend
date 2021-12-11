package polsl.pl.IoTBE.message.channel;

import lombok.Getter;
import org.springframework.boot.configurationprocessor.json.JSONException;
import polsl.pl.IoTBE.domain.VirtualObject;

@Getter
public abstract class VirtualChannel<T> {

    public VirtualChannel(String type) {
        this.type = type;
    }

    protected String type;
    public abstract Boolean executeMessage(String msg, T virtualDevice);
    public abstract void sendGetSignalToMqtt(String topic, String payload) ;
}
