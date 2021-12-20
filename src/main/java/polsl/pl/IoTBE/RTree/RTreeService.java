package polsl.pl.IoTBE.RTree;

import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Geometry;
import com.github.davidmoten.rtree.geometry.Rectangle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.integration.graph.TimerStats;
import org.springframework.stereotype.Service;
import polsl.pl.IoTBE.common.MqttConfigValues;
import polsl.pl.IoTBE.common.RTreeConfigValues;
import polsl.pl.IoTBE.domain.VirtualObject;
import polsl.pl.IoTBE.exceptions.NoResponseFromSensorException;
import polsl.pl.IoTBE.mqtt.MqttController;
import polsl.pl.IoTBE.storage.StorageMenager;
import rx.Observable;
import rx.Observer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RTreeService {
    @Autowired
    RTreeManager rTreeManager;
    @Autowired
    StorageMenager storageMenager;
    @Autowired
    MqttController mqttController;

    List<String> searchThroughRTree(Rectangle searchRectangle){

        //rTreeManager.create(SupportedMyRTreeNodeTypes.doubleNode);
       //this.addTestNodesToRTree();

      //  String tempRTreeType = SupportedMyRTreeNodeTypes.doubleNode.toString()+ RTreeConfigValues.rTreeNameSuffix;

        Observable<Entry<MyRTreeNode, Geometry>> results = rTreeManager.search(searchRectangle,TreeTypes.DoubleNode);


        List<MyRTreeNode> foundNodes = new ArrayList<>();
        List<String> topicPrefixes = new ArrayList<>();
        List<VirtualObject> virtualObjectList = new ArrayList<>();

        results.subscribe(entry ->{
            foundNodes.add(entry.value());
        });

        foundNodes.forEach(node -> {
            virtualObjectList.add(node.getVirtualObject());
            topicPrefixes.add(node.value() + MqttConfigValues.sendRequestSuffix);
        });
        results.toBlocking().first();

        sendSignalsToMqtt(topicPrefixes);

        getMeasurementsTest(virtualObjectList);

        updateNodeHistories(foundNodes);

        topicPrefixes.add(0,"got all readings :))");

        return fetchResults(foundNodes);
    }

    private List<String> fetchResults(List<MyRTreeNode> foundNodes){
        List<String> results = new ArrayList<>();
        foundNodes.forEach(node ->{
            String result = node.getVirtualObject().getTopicPrefix() + " reading: " + node.getVirtualObject().getValue();
            results.add(result);
        });
        return results;
    }

    private void updateNodeHistories(List<MyRTreeNode> foundNodes){
        foundNodes.forEach(node ->{
            node.getRecordList().addRecord(new Record<Double>(node.getVirtualObject().getValue(), node.getVirtualObject().getLastValueTimestamp()));
        });
    }

    private void sendSignalsToMqtt(List<String> topicPrefixes){
        topicPrefixes.forEach(topic ->{
            mqttController.publish(topic, MqttConfigValues.getMeasurementPayload);
        });

    }

    private void getMeasurementsTest(List<VirtualObject> virtualObjectList){
        List<Timestamp> timestampList = new ArrayList<>();
        virtualObjectList.forEach(vo ->{
            timestampList.add(vo.getLastValueTimestamp());
        });

        int counter = 0;
        boolean allMeasurmentsDone = false;


        while(counter < MqttConfigValues.waitForMeasurementResponseTimeSeconds && !allMeasurmentsDone){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }

            boolean gotAll = true;
            for(int i=0; i<timestampList.size(); i++){
                if(timestampList.get(i) == virtualObjectList.get(i).getLastValueTimestamp())
                {
                   gotAll = false;
                }
            }
            if(gotAll){
                allMeasurmentsDone = true;
            }

            counter++;
        }


         if(!allMeasurmentsDone){
             List<String> noResposneSensorList = new ArrayList<>();
             for(int i=0; i<timestampList.size(); i++){
                 if(timestampList.get(i) == virtualObjectList.get(i).getLastValueTimestamp())
                 {
                     noResposneSensorList.add(virtualObjectList.get(i).getTopicPrefix());
                 }
             }
             throw new NoResponseFromSensorException(noResposneSensorList);

         }


    }

    private void addTestNodesToRTree(){


        VirtualObject vo = storageMenager.getVirtualObjectList().get(0);

        rTreeManager.addDoubleNode(vo, Geometries.circle(5, 5, 5), TreeTypes.DoubleNode);

        for(int i=1; i<100; i+=3){
            rTreeManager.addDoubleNode(vo,Geometries.circle(i, i, 1), TreeTypes.DoubleNode);
        }
       // rTreeManager.visualize(600,600,RTreeConfigValues.visualizationPath,);
    }
}
