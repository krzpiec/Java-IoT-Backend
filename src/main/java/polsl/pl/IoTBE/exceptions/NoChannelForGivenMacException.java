package polsl.pl.IoTBE.exceptions;

import lombok.Getter;

@Getter
public class NoChannelForGivenMacException extends RuntimeException{

    private String mac;
    private long channelNumber;

    public NoChannelForGivenMacException(String mac, long channelNumber){
        super();
        this.mac = mac;
        this.channelNumber = channelNumber;
    }
}
