package polsl.pl.IoTBE.rest.dto;

import lombok.Data;

@Data
public class VirtualSensorInitDto {

String mac;
String channelNumber;
String unit;
String description;
String latitude;
String longitude;
}
