package polsl.pl.IoTBE.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import polsl.pl.IoTBE.exceptions.*;
import polsl.pl.IoTBE.responseComminicates.DeviceDtoResponse;
import polsl.pl.IoTBE.responseComminicates.VirtualObjectDtoResponse;
import polsl.pl.IoTBE.rest.dto.VirtualObjectDto;

import java.util.List;

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

    @ExceptionHandler(value = {NoDeviceWithGivenMacException.class})
    protected ResponseEntity<String> handleInvalidMacPassed(NoDeviceWithGivenMacException noDeviceWithGivenMac){

        return new ResponseEntity<String>("Device with mac:  "+  noDeviceWithGivenMac.getMessage() + " doesnt exist in database", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {NoChannelForGivenMacException.class})
    protected ResponseEntity<String> handleInvalidChannelNumberForMac(NoChannelForGivenMacException noChannelForGivenMacException){

        return new ResponseEntity<String>("Channel with number:  "+  noChannelForGivenMacException.getChannelNumber() + " doesnt exist in device with mac: " + noChannelForGivenMacException.getMac(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ChannelTakenException.class})
    protected ResponseEntity<VirtualObjectDtoResponse> handleTakenChannel(ChannelTakenException channelTakenException){

        return new ResponseEntity<VirtualObjectDtoResponse>(channelTakenException.getVirtualObjectDtoResponse(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {InvalidMqttMessageException.class})
    protected ResponseEntity<String> handleInvalidMqttMessage(InvalidMqttMessageException invalidMqttMessageException){

        return new ResponseEntity<String>(invalidMqttMessageException.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {NoResponseFromSensorException.class})
    protected ResponseEntity<List<String>> handleNoResponseFromSensors(NoResponseFromSensorException noResponseFromSensorException){

        return new ResponseEntity<List<String>>(noResponseFromSensorException.getTopicPrefixes(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {WrongPayloadException.class})
    protected ResponseEntity<String> handleWrondPayloadException(WrongPayloadException wrongPayloadException){

        return new ResponseEntity<String>(wrongPayloadException.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {InvalidTypeForChannelException.class})
    protected ResponseEntity<String> invalidTypeForChannel(InvalidTypeForChannelException invalidTypeForChannelException){

        String message = invalidTypeForChannelException.getMessage() + " desired type: " + invalidTypeForChannelException.getDesiredType();
        return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
    }
}
