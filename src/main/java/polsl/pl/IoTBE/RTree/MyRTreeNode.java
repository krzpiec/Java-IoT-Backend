package polsl.pl.IoTBE.RTree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import polsl.pl.IoTBE.domain.VirtualObject;
import polsl.pl.IoTBE.message.channel.VirtualChannel;

import java.util.ArrayList;


@Getter
@Setter
public class MyRTreeNode<T> {
    VirtualObject virtualObject;
    RecordList<Record<T>> recordList;

    MyRTreeNode(VirtualObject virtualObject, int maxRecordListSize){
        this.virtualObject = virtualObject;
        this.recordList = new RecordList<>(maxRecordListSize);

    }

    public String value(){
        return this.virtualObject.getTopicPrefix();
    }
}
