package polsl.pl.IoTBE.responseComminicates;

import lombok.Data;

import java.util.List;

@Data
public class ResponseMessageAndList<T> {

    String message;
    List<T> list;
}
