package polsl.pl.IoTBE.message.channel;

import lombok.Getter;

@Getter
public abstract class VirtualChannel<T> {

    public VirtualChannel(String type) {
        this.type = type;
    }

    protected String type;
    public abstract Boolean executeMessage(String msg, T virtualDevice);
}
