package polsl.pl.IoTBE.storage;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.stereotype.Component;
import polsl.pl.IoTBE.domain.VirtualObject;
import polsl.pl.IoTBE.message.channel.TempSensorChannel;
import polsl.pl.IoTBE.message.channel.VirtualChannel;
import polsl.pl.IoTBE.message.handler.MqttMessageHandler;
import polsl.pl.IoTBE.mqtt.MqttSubscriberConfig;
import polsl.pl.IoTBE.repository.DeviceRepository;
import polsl.pl.IoTBE.repository.dao.Channel;
import polsl.pl.IoTBE.repository.dao.Device;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Data
@AllArgsConstructor
public class StorageMenager {


    @Autowired
    Dbloader dbloader;
    @Autowired
    MqttMessageHandler mqttMessageHandler;
    @Autowired
    MqttPahoMessageDrivenChannelAdapter adapter;


    List<VirtualObject> virtualObjectList;
    List<Device> deviceList;
    List<Channel> channelList;
    List<VirtualChannel> virtualChannelList;


    @PostConstruct
    private void initSystem() {


        mqttMessageHandler.setStorageMenager(this);
        this.deviceList = dbloader.getAllDevices();
        this.channelList = dbloader.getAllChannels();

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



           this.virtualObjectList.forEach(virtualObject -> {
               this.adapter.addTopic(virtualObject.getTopicPrefix());
           });

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

    //bad channel num or mac handla that


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



}
