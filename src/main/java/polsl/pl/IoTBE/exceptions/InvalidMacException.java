package polsl.pl.IoTBE.exceptions;

import lombok.Data;

@Data
public class InvalidMacException extends RuntimeException{

    String errorCode;
    String errorMessage;

    public InvalidMacException(String mac){
        super(mac);
        this.errorMessage = "Invalid mac adress";
        this.errorCode = "69";
    }


}
