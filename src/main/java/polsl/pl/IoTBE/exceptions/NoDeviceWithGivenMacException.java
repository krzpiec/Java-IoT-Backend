package polsl.pl.IoTBE.exceptions;

public class NoDeviceWithGivenMacException extends RuntimeException{
    public NoDeviceWithGivenMacException(String mac){
        super(mac);
    }
}
