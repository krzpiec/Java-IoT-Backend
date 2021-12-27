package polsl.pl.IoTBE.exceptions;

import lombok.Data;
import polsl.pl.IoTBE.rest.dto.DeviceDescriptionDto;
import polsl.pl.IoTBE.rest.dto.DeviceDto;

@Data
public class DevicePresentException extends RuntimeException{


    String errorCode;
    String errorMessage;
    DeviceDto deviceDto;

    public DevicePresentException(DeviceDto deviceDto){
        super();
        this.deviceDto = deviceDto;
        this.errorMessage = "Device with this mac already added";
        this.errorCode = "69";
    }
}
