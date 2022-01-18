package polsl.pl.IoTBE.RTree;

import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Rectangle;
import com.sun.source.tree.Tree;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import polsl.pl.IoTBE.common.RTreeConfigValues;
import polsl.pl.IoTBE.domain.VirtualObject;
import rx.Observable;

import java.util.HashMap;
import java.util.Map;


@Component
@Data
public class RTreeManager {

    @Autowired
    RTreeFactory rTreeFactory;

    Map<TreeTypes, RTree> rTreeMap = new HashMap<>();



    public void deleteTree(TreeTypes type){
        this.rTreeMap.put(type, rTreeFactory.createRTreeFromParam(SupportedMyRTreeNodeTypes.doubleNode));
    }

    public void create(SupportedMyRTreeNodeTypes type){
        TreeTypes mapKey = TreeTypes.values()[type.ordinal()];
        if(this.rTreeMap.get(mapKey) == null){
            this.rTreeMap.put(mapKey, rTreeFactory.createRTreeFromParam(type));
        }

    }
    public void addDoubleNode(VirtualObject virtualObject, Geometry geometry, TreeTypes type){
        RTree rTree =  this.rTreeMap.get(type);

        rTree = rTree.add(new MyRTreeNode<Double>(virtualObject, RTreeConfigValues.maxRecordListSize), geometry);

        this.rTreeMap.put(type,rTree);
    }



//    public void addBooleanNode(VirtualObject virtualObject, Geometry geometry){
//        this.rTree = this.rTree.add(new MyRTreeNode<Boolean>(virtualObject), geometry);
//    }
    public void visualize(int width, int height, String path, String treeName){
        this.rTreeMap.get(treeName).visualize(width,height).save(path);
    }
    public Observable<Entry<MyRTreeNode, Geometry>> search(Rectangle searchRectangle, TreeTypes type){
        return this.rTreeMap.get(type).search(searchRectangle);
    }

}
