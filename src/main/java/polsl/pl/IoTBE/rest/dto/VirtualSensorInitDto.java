package polsl.pl.IoTBE.rest.dto;

import lombok.Data;

@Data
public class VirtualSensorInitDto {

String macAdr;
String channelNumber;
String unit;
String description;
double latitude;
double longitude;
}
