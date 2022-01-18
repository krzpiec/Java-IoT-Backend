package polsl.pl.IoTBE.storage;


import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Rectangle;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.stereotype.Component;
import polsl.pl.IoTBE.RTree.RTreeManager;
import polsl.pl.IoTBE.RTree.RectangleDto;
import polsl.pl.IoTBE.RTree.SupportedMyRTreeNodeTypes;
import polsl.pl.IoTBE.RTree.TreeTypes;
import polsl.pl.IoTBE.common.MqttConfigValues;
import polsl.pl.IoTBE.domain.VirtualObject;
import polsl.pl.IoTBE.domain.VirtualTermometer;
import polsl.pl.IoTBE.message.channel.TempSensorChannel;
import polsl.pl.IoTBE.message.channel.VirtualChannel;
import polsl.pl.IoTBE.message.handler.MqttMessageHandler;
import polsl.pl.IoTBE.mqtt.MqttController;
import polsl.pl.IoTBE.mqtt.MqttSubscriberConfig;
import polsl.pl.IoTBE.repository.DeviceRepository;
import polsl.pl.IoTBE.repository.dao.Channel;
import polsl.pl.IoTBE.repository.dao.Device;
import polsl.pl.IoTBE.repository.dao.Localization;
import polsl.pl.IoTBE.rest.dto.EditVirtualObjectDto;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
@Data
@AllArgsConstructor
public class StorageMenager {


    @Autowired
    Dbloader dbloader;
    @Autowired
    MqttMessageHandler mqttMessageHandler;

    //For test purposes
    @Autowired
    MqttController mqttController;
    @Autowired
    MqttPahoMessageDrivenChannelAdapter adapter;
    @Autowired
    RTreeManager rTreeManager;

    List<VirtualObject> virtualObjectList;
    List<Device> deviceList;
    List<Channel> channelList;
    List<VirtualChannel> virtualChannelList;


    @PostConstruct
    private void initSystem() {


        mqttMessageHandler.setStorageMenager(this);
        this.deviceList = dbloader.getAllDevices();
        this.channelList = dbloader.getAllChannels();


        Map<String, List<Channel>> deviceAddChannelsMap = new TreeMap<>();
        this.deviceList.forEach(device ->{
            List<Channel> deviceChannels = new ArrayList<>();
            deviceAddChannelsMap.put(device.getMacAdr(), deviceChannels);
        });

        this.channelList.forEach(channel -> {
            deviceAddChannelsMap.get(channel.getDevice().getMacAdr()).add(channel);
        });

        this.deviceList.forEach(device -> {
            device.setChannels(deviceAddChannelsMap.get(device.getMacAdr()));
        });

        Set<String> channelTypes = new HashSet<>();
        this.channelList.forEach(
                channel -> {
            channelTypes.add(channel.getType());
        });

        this.virtualObjectList = dbloader.initializeVirtualObjectsFromDataBase();
        this.virtualObjectList.forEach(virtualObject -> {
            virtualObject.setVirtualChannel(
                    getVirtualChannelByType(getChannelByMacAndChannelNumber(virtualObject.getMac(), virtualObject.getChannelNumber()).getType())
                            );
        });

        this.createDuzoSensorow();

        System.out.println(this.virtualObjectList.size());
           this.virtualObjectList.forEach(virtualObject -> {
              // this.adapter.addTopic(virtualObject.getTopicPrefix() + MqttConfigValues.receiveRequestSuffix);
           });
          rTreeManager.create(SupportedMyRTreeNodeTypes.doubleNode);


          //todo stworzenie geometry z dobra lokalizacja

          this.virtualObjectList.forEach(vo ->{
              rTreeManager.addDoubleNode(vo, Geometries.circle(vo.getLocalization().getLatitude(), vo.getLocalization().getLongitude(), 0.00000001) , TreeTypes.DoubleNode);
          });
    }


    public void sendTopicsToSubscribeToDeviceSimulator() {
        String topics = "";
        StringBuilder topicBuilder = new StringBuilder(topics);
        this.virtualObjectList.forEach(vo -> {
            topicBuilder.append(vo.getTopicPrefix() + MqttConfigValues.sendRequestSuffix + "//");
        });

        topics = topicBuilder.toString();
        mqttController.publish(MqttConfigValues.DeviceSimulatorSendTopicsToSubscribe, topics);
    }


    private void createDuzoSensorow(){

        int organization = 2;
        int nextVoSwitch  = 0;
        int nVo = 100;
        double latitude = 51.5;
        double longitude = 21.0;
        double stepInLongitude = 0.01;
        double nI = 0;
        double nJ = 0;

        switch (organization){
            case 0:{//linia
                    nI = nVo;
                    nJ = 1;
                    break;
            }
            case 1:{//kwadrat
                nI = nJ = Math.sqrt(nVo);
                break;
            }
            case 2:{
                createRandom(nVo, longitude, latitude, stepInLongitude);
                return;
            }
        }
        System.out.println(nI + " "+nJ);
        for(int i=0; i<nI; i++ ){
            for(int j=0; j<nJ; j++){
                //create localization
                Localization localization = new Localization();
                localization.setLongitude(longitude+i*stepInLongitude);
                localization.setLatitude(latitude+j*stepInLongitude);
                //create vo
                VirtualChannel virtualChannel = this.getVirtualChannelByType("Sensor");
                VirtualTermometer virtualTermometer = new VirtualTermometer(
                        "test"+nextVoSwitch,
                        i,
                        virtualChannel,
                        localization,
                        "Jednostka",
                        0,
                        "Sensor"
                );
                nextVoSwitch++;
                this.virtualObjectList.add(virtualTermometer);
            }

        }



    }

    public long searchinggggggg(Rectangle rectangle)    {
        long start = System.nanoTime();
        int count = 0;
        for(int i=0; i<this.virtualObjectList.size(); i++){
            double x = this.virtualObjectList.get(i).getLocalization().getLongitude();
            double y = this.virtualObjectList.get(i).getLocalization().getLatitude();
            if(x-0.00001 > rectangle.x1() && x+0.00001 < rectangle.x2() && y -0.00001> rectangle.y1() && y+0.00001< rectangle.y2()){
                count++;
            }

        }
        long end = System.nanoTime();
      //  System.out.println(count);
        return end - start;
    }

    private void createRandom(int nVo, double longitude, double latitude, double stepInLongitude){
        int nextVoSwitch = 0;
        System.out.println(nVo);
        for(int i=0; i<nVo; i++ ){

                //create localization
                Localization localization = new Localization();
                localization.setLongitude(Math.random() * longitude+50 );
                localization.setLatitude(Math.random() * latitude+50);
                //create vo
                VirtualChannel virtualChannel = this.getVirtualChannelByType("Sensor");
                VirtualTermometer virtualTermometer = new VirtualTermometer(
                        "test"+nextVoSwitch,
                        i,
                        virtualChannel,
                        localization,
                        "Jednostka",
                        0,
                        "Sensor"
                );
                nextVoSwitch++;
                this.virtualObjectList.add(virtualTermometer);


        }

    }

    public void recunstructRTree(){
        this.rTreeManager.deleteTree(TreeTypes.DoubleNode);
        this.virtualObjectList.forEach(vo ->{
            rTreeManager.addDoubleNode(vo, Geometries.circle(vo.getLocalization().getLatitude(), vo.getLocalization().getLongitude(), 0.00000001) , TreeTypes.DoubleNode);
        });
        System.out.println("b");
    }

    public void addVirtualObject(VirtualObject virtualObject)
    {
        this.virtualObjectList.add(virtualObject);
    }


    private VirtualChannel createVirtualChannelByType(String type)
    {
        switch(type)
        {
            case "Sensor":
                return new TempSensorChannel("Sensor");
        }

        return null;
    }

    public List<Channel> getChannelsByMac(String macAdr){
        List <Channel> channelList = new ArrayList<>();
        this.channelList.forEach(channel -> {
            if(channel.getDevice().getMacAdr().equals(macAdr))
                channelList.add(channel);
        });
        return channelList;
    }

    public String getTypeByMacAndChannelNumber(String mac, long channelNumber) {


        for(Channel channel: this.channelList){

            if(channel.getChannelNumber() == channelNumber && channel.getDevice().getMacAdr().equals(mac))
                return channel.getType();
        }

        return null;
    }

    public VirtualChannel getVirtualChannelByType(String type) {
        VirtualChannel virtualChannel = this.getVirtualChannelList().stream()
                .filter(virtualChannel1 -> virtualChannel1.getType().equals(type))
                .findAny()
                .orElse(null);
        return virtualChannel;
    }

    public VirtualObject getVirtualObjectByMacAndChannelNumber(String mac, long channelNumber) {

        VirtualObject virtualObject = this.getVirtualObjectList().stream()
                .filter(virtualDevice1 -> virtualDevice1.getMac().equals(mac) && virtualDevice1.getChannelNumber() == channelNumber)
                .findAny()
                .orElse(null);

        return virtualObject;

    }


    public Channel getChannelByMacAndChannelNumber(String mac, Long channelNumber){
        for(Channel channel: this.channelList){
            if(channel.getDevice().getMacAdr().equals(mac) && channel.getChannelNumber() == channelNumber)
                return channel;
        }
        return null;
    }

    public void addChannel(Channel channel) {
        this.addChannel(channel);
    }

    public void addChannelsFromChannelList(List<Channel> channelList) {
        this.channelList.addAll(channelList);
    }

    public void addVirtualChannelsFromChannelList(List<Channel> channelList) {
        for (Channel channel : channelList) {
            boolean apperance = false;
            for (VirtualChannel virtualChannel : this.virtualChannelList) {
                if (virtualChannel.getType().equals(channel.getType()))
                    apperance = true;
            }
            if (!apperance) {
                switch (channel.getType()) {
                    case "Sensor": {
                        VirtualChannel virtualChannel = new TempSensorChannel("Sensor");
                        this.virtualChannelList.add(virtualChannel);
                    }
                }

            }

        }
    }

    public void addDevice(Device device) {
        this.deviceList.add(device);
    }

    public Device getDeviceByMac(String mac) {
        for (Device device : this.deviceList) {
            if (device.getMacAdr().equals(mac))
                return device;
        }
        return null;
    }

    public Device isDevicePresent(Device device) {
        for (Device device1 : this.deviceList) {
            if (device1.getMacAdr().equals(device.getMacAdr()))
                return device1;
        }
        return null;
    }

    public boolean isMacPresent(String mac){
        for (Device device1 : this.deviceList) {
            if (device1.getMacAdr().equals(mac))
                return true;
        }
        return false;
    }


}
