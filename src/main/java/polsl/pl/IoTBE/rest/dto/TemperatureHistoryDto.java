package polsl.pl.IoTBE.rest.dto;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class TemperatureHistoryDto {

    private int value;
    private Timestamp measureTime;
}
