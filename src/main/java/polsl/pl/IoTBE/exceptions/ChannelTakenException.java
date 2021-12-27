package polsl.pl.IoTBE.exceptions;

import lombok.Getter;

import polsl.pl.IoTBE.responseComminicates.VirtualObjectDtoResponse;
import polsl.pl.IoTBE.rest.dto.VirtualObjectDto;

@Getter
public class ChannelTakenException extends RuntimeException{

    VirtualObjectDtoResponse virtualObjectDtoResponse;
    public ChannelTakenException(String message, VirtualObjectDto virtualObjectDto){
            super();
            this.virtualObjectDtoResponse = new VirtualObjectDtoResponse(message, virtualObjectDto);
    }
}
