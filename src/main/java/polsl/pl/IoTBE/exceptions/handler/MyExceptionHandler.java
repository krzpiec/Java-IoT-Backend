package polsl.pl.IoTBE.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import polsl.pl.IoTBE.exceptions.DevicePresentException;
import polsl.pl.IoTBE.exceptions.InvalidConfigException;
import polsl.pl.IoTBE.exceptions.InvalidMacException;
import polsl.pl.IoTBE.exceptions.TopicAlreadySubscribedException;
import polsl.pl.IoTBE.responseComminicates.DeviceDtoResponse;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InvalidMacException.class})
    protected ResponseEntity<String> handleInvalidMac(InvalidMacException invalidMacException){
    return new ResponseEntity<String>(invalidMacException.getErrorMessage()+", passed mac: "+invalidMacException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DevicePresentException.class})
    protected ResponseEntity<DeviceDtoResponse> handleDevicePresentException(DevicePresentException devicePresentException){


        DeviceDtoResponse deviceDtoResponse = new DeviceDtoResponse();
       deviceDtoResponse.setDeviceDto(devicePresentException.getDeviceDto());
        deviceDtoResponse.setMesssage("Device already present");

        return new ResponseEntity<DeviceDtoResponse>(deviceDtoResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {InvalidConfigException.class})
    protected ResponseEntity<String> handleInvalidConfigJson(InvalidConfigException invalidConfigException){

        return new ResponseEntity<String>(invalidConfigException.getMessage()+ " at " + invalidConfigException.getWhere(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {TopicAlreadySubscribedException.class})
    protected ResponseEntity<String> handleInvalidConfigJson(TopicAlreadySubscribedException topicAlreadySubscribedException){

        return new ResponseEntity<String>("Config get topic already subscribed for "+ topicAlreadySubscribedException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
