package polsl.pl.IoTBE.RTree;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Record<T> {
    T value;
    Timestamp timestamp;

}
