package polsl.pl.IoTBE.TestingRtree;

import com.github.davidmoten.rtree.geometry.Geometries;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class RtreeTestInputDto {

    List<RectangleDto> entries;
    //private RectangleDto searchRectangle;
}
