package polsl.pl.IoTBE.rest.dto;

import lombok.Data;

@Data
public class VirtualObjectDto {
    String mac;
    long channelNumber;
    LocalizationDto localizationDto;
    String description;
    String desiredType;
}
