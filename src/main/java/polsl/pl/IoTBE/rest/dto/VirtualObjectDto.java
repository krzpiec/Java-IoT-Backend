package polsl.pl.IoTBE.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VirtualObjectDto {
    String mac;
    long channelNumber;
    LocalizationDto localizationDto;
    String description;
    String desiredType;
}
