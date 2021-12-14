package polsl.pl.IoTBE.RTree;

import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Rectangle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polsl.pl.IoTBE.domain.VirtualObject;
import polsl.pl.IoTBE.storage.StorageMenager;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

@Service
public class RTreeService {
    @Autowired
    RTreeManager rTreeManager;
    @Autowired
    StorageMenager storageMenager;


    List<String> searchThroughRTree(Rectangle searchRectangle){


        rTreeManager.create(SupportedMyRTreeNodeTypes.doubleNode);


       this.addTestNodesToRTree();
        String tempRTreeType = SupportedMyRTreeNodeTypes.doubleNode.toString()+"NodeRTree";

        Observable<Entry<MyRTreeNode, Geometry>> results = rTreeManager.search(searchRectangle,tempRTreeType);

        List<String> topicPrefixes = new ArrayList<>();
        results.subscribe(entry ->{
           topicPrefixes.add(entry.value().value());
        });
        return topicPrefixes;
    }

    private void addTestNodesToRTree(){
        String tempRTreeType = SupportedMyRTreeNodeTypes.doubleNode.toString()+"NodeRTree";
        VirtualObject vo = storageMenager.getVirtualObjectList().get(0);

        rTreeManager.addDoubleNode(vo, Geometries.circle(5, 5, 5), tempRTreeType);

        for(int i=1; i<100; i+=3){
            rTreeManager.addDoubleNode(vo,Geometries.circle(i, i, 1), tempRTreeType);
        }

        rTreeManager.visualize(600,600,"D:/Inzynierka/mytree.png", tempRTreeType);
    }
}
