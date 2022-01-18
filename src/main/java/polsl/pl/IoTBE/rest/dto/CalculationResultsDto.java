package polsl.pl.IoTBE.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculationResultsDto {
    private double avarage;
    private double domimant;
    private double standardDeviation;
    private double median;
    private double max;
    private double min;


}
