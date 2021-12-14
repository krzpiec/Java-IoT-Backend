package polsl.pl.IoTBE.RTree;

import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Rectangle;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import polsl.pl.IoTBE.domain.VirtualObject;
import rx.Observable;

import java.util.HashMap;
import java.util.Map;


@Component
@Data
public class RTreeManager {

    @Autowired
    RTreeFactory rTreeFactory;

    Map<String, RTree> rTreeMap = new HashMap<>();


    public void create(SupportedMyRTreeNodeTypes type){
        String mapKey = type.toString() + "NodeRTree";
        if(this.rTreeMap.get(mapKey) == null){
            this.rTreeMap.put(mapKey, rTreeFactory.createRTreeFromParam(type));
        }

    }
    public void addDoubleNode(VirtualObject virtualObject, Geometry geometry, String treeName){
        RTree rTree =  this.rTreeMap.get(treeName);

        rTree = rTree.add(new MyRTreeNode<Double>(virtualObject), geometry);

        this.rTreeMap.put(treeName,rTree);
    }
//    public void addBooleanNode(VirtualObject virtualObject, Geometry geometry){
//        this.rTree = this.rTree.add(new MyRTreeNode<Boolean>(virtualObject), geometry);
//    }
    public void visualize(int width, int height, String path, String treeName){
        this.rTreeMap.get(treeName).visualize(width,height).save(path);
    }
    public Observable<Entry<MyRTreeNode, Geometry>> search(Rectangle searchRectangle, String treeName){
        return this.rTreeMap.get(treeName).search(searchRectangle);
    }

}
