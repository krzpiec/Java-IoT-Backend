package polsl.pl.IoTBE.rest.dto;

import lombok.Data;
import polsl.pl.IoTBE.repository.dao.Localization;

import javax.persistence.Column;

@Data
public class LocalizationDto {

    public LocalizationDto(Localization localization, String description)
    {
        this.latitude = localization.getLatitude();
        this.longitude = localization.getLongitude();
        this.description = description;
    }
    private String description;

    private double latitude;

    private  double longitude;
}
