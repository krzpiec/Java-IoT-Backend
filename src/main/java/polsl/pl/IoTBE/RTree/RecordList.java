package polsl.pl.IoTBE.RTree;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RecordList<Record> {


    private int maxSize;
    private List<Record> recordList;

    public RecordList(int maxSize) {
        this.maxSize = maxSize;
        this.recordList = new ArrayList<>();
    }

    void addRecord(Record record){
        if(recordList.size() == maxSize){
            recordList.remove(maxSize-1);
        }
        this.recordList.add(0,record);
    }
}
