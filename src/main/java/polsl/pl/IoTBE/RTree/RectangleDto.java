package polsl.pl.IoTBE.RTree;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RectangleDto {

    private String name;
    private double x1;
    private double y1;
    private double x2;
    private double y2;
}