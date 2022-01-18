package polsl.pl.IoTBE.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditVirtualObjectDto {
    private String mac;
    private long channelNumber;
    private String localizationDescription;
    private String description;
    double latitude;
    double longitude;
}
