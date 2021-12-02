package polsl.pl.IoTBE.rest.dto;

import lombok.Data;

@Data
public class TermometerDescriptionDto {

    private LocalizationDto localizationDto;
    private String unit;
    private String description;
}
