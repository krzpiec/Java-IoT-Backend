package polsl.pl.IoTBE.RTree;

import lombok.Data;

import java.util.List;

@Data
public class RecordList<Record> {

    private int maxSize;
    private List<Record> recordList;


    void addRecord(Record record){
        this.recordList.add(record);
    }
}
