package polsl.pl.IoTBE.RTree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import polsl.pl.IoTBE.domain.VirtualObject;
import polsl.pl.IoTBE.message.channel.VirtualChannel;


@Getter
@Setter
public class MyRTreeNode<T> {

    MyRTreeNode(VirtualObject virtualObject){
        this.virtualObject = virtualObject;

    }
    VirtualObject virtualObject;
    RecordList<Record<T>> recordList;
    public String value(){
        return this.virtualObject.getTopicPrefix();
    }
}
