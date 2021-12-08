package polsl.pl.IoTBE.rest.dto;

import lombok.Data;

@Data
public class VirtualSensorDto {
    String mac;
    String channelNumber;
    LocalizationDto localizationDto;
    String virtualChannelType;
    String unit;
    String lastReadValue;
    String description;
}
