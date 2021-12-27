package polsl.pl.IoTBE.exceptions;

public class InvalidMqttMessageException extends RuntimeException{
    //todo upgrade this
    public InvalidMqttMessageException(String message){

        super("Invalid mqtt message at " + message);
    }
}
