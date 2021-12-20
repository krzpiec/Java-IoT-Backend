package polsl.pl.IoTBE.exceptions;

import lombok.Getter;

@Getter
public class WrongPayloadException extends RuntimeException{
    public WrongPayloadException(String message){
        super(message);
    }
}
