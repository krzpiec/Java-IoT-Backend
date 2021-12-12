package polsl.pl.IoTBE.responseComminicates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import polsl.pl.IoTBE.rest.dto.VirtualObjectDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VirtualObjectDtoResponse {
    String message;
    VirtualObjectDto virtualObjectDto;
}
