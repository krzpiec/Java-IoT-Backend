package polsl.pl.IoTBE.TestingRtree;

import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Rectangle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;

import java.util.List;

@RestController
public class RtreeTestController {



    @PostMapping("/rtree")
    public ResponseEntity<String> CheckRTree(@RequestBody RtreeTestInputDto RtreeTestInputDto){



        RectangleDto searchRectangleDto = RtreeTestInputDto.getEntries().get(RtreeTestInputDto.getEntries().size()-1);
        Rectangle searchRectangle = Geometries.rectangle(searchRectangleDto.getX1(), searchRectangleDto.getY1(), searchRectangleDto.getX2(), searchRectangleDto.getY2());
        List<RectangleDto> rectangleDtoList = RtreeTestInputDto.getEntries();
        rectangleDtoList.remove(rectangleDtoList.size()-1);



        RTree<String, Geometry> tree = RTree.create();

//        for(RectangleDto rectangle : rectangleDtoList){
//            tree = tree.add(rectangle.getName(), Geometries.rectangle(rectangle.getX1(), rectangle.getY1(), rectangle.getX2(), rectangle.getY2()));
//        }
       // tree = tree.add("name",Geometries.rectangle(0, 0, 10, 10 ));
        for(int i=1; i<100; i+=3){
            tree = tree.add("name"+i,Geometries.rectangle(i, 1, i+2, 4 ));
        }
        tree.visualize(600,600)
                .save("D:/Inzynierka/mytree.png");

        Observable<Entry<String, Geometry>> results =
                tree.search(Geometries.rectangle(1,0.5,6,5));
      results.subscribe(entry ->{
          System.out.println(entry.value());
      });
    return ResponseEntity.ok("result");

    }
}
