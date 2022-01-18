package polsl.pl.IoTBE.exceptions;

import lombok.Getter;

@Getter
public class InvalidTypeForChannelException extends RuntimeException{

    String desiredType;
    public InvalidTypeForChannelException(String desiredtype){
        super("Wrong channelType");
        this.desiredType = desiredtype;
    }
}
