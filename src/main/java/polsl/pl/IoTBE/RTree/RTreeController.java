package polsl.pl.IoTBE.RTree;

import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Rectangle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import polsl.pl.IoTBE.RTree.testing.RectangleDto;
import polsl.pl.IoTBE.RTree.testing.RtreeTestInputDto;
import polsl.pl.IoTBE.domain.VirtualObject;
import polsl.pl.IoTBE.storage.StorageMenager;
import rx.Observable;

import java.util.List;

@RestController
public class RTreeController {


    @Autowired
    RTreeService rTreeService;

    @PostMapping("/rtree/search")
    public ResponseEntity<List<String>> searchThroughRTree(@RequestBody RectangleDto rectangleDto){

        Rectangle searchRectangle = Geometries.rectangle(rectangleDto.getX1(), rectangleDto.getY1(), rectangleDto.getX2(), rectangleDto.getY2());

        return ResponseEntity.ok( rTreeService.searchThroughRTree(searchRectangle));

    }


}
