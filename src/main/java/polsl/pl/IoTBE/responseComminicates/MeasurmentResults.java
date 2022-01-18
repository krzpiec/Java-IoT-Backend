package polsl.pl.IoTBE.responseComminicates;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MeasurmentResults {
    private String mac;
    private long channelNumber;
    private String reading;
}
