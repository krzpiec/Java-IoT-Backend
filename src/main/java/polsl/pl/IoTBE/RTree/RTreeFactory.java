package polsl.pl.IoTBE.RTree;

import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.*;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class RTreeFactory {

    RTree createRTreeFromParam(SupportedMyRTreeNodeTypes type){
        switch(type){
            case doubleNode: {
                RTree<MyRTreeNode<Double>, Circle> rTree = RTree.create();
                return rTree;
            }
            case BinaryNode: {
                RTree<MyRTreeNode<Boolean>, Point> rTree = RTree.create();
                return rTree;
            }
            case intNode: {
                RTree<MyRTreeNode<Integer>, Circle> rTree = RTree.create();
                return rTree;
            }
            default:
            {
                //todo exception
            }
        }
        return null;//
    }
}
