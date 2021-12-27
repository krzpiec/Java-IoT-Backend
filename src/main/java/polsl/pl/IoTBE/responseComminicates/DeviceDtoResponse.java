package polsl.pl.IoTBE.responseComminicates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import polsl.pl.IoTBE.rest.dto.DeviceDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDtoResponse {


    String messsage;
    DeviceDto deviceDto;



}
