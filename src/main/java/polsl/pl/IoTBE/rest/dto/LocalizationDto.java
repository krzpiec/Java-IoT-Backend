package polsl.pl.IoTBE.rest.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class LocalizationDto {

    private String description;

    private double latitude;

    private  double longitude;
}
