package polsl.pl.IoTBE.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import polsl.pl.IoTBE.repository.dao.Localization;

import javax.persistence.Column;


@Getter
@Setter
public class LocalizationDto {

    public LocalizationDto(Localization localization, String description)
    {
        this.latitude = localization.getLatitude();
        this.longitude = localization.getLongitude();
        this.description = localization.getDescription();
    }
    private String description;

    private double latitude;

    private  double longitude;
}
