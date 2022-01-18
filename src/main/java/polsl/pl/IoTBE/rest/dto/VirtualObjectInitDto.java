package polsl.pl.IoTBE.rest.dto;

import lombok.Data;

@Data
public class VirtualObjectInitDto {

    String macAdr;
    long channelNumber;
    String type;
}
