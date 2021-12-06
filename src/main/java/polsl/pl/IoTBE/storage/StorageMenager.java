package polsl.pl.IoTBE.storage;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import polsl.pl.IoTBE.domain.VirtualObject;
import polsl.pl.IoTBE.message.channel.TempSensorChannel;
import polsl.pl.IoTBE.message.channel.VirtualChannel;
import polsl.pl.IoTBE.repository.dao.Channel;
import polsl.pl.IoTBE.repository.dao.Device;

import java.util.List;

@Component
@Data
@AllArgsConstructor
public class StorageMenager {

    List<VirtualObject> virtualObjectList;
    List<Device> deviceList;
    List<Channel> channelList;
    List<VirtualChannel> virtualChannelList;


    public String getTypeByMacAndChannelNumber(String mac, long channelNumber)
    {
        //error thrown if null
        Device device = this.getDeviceList().stream()
                .filter(device1 -> mac.equals(device1.getMacAdr()))
                .findAny()
                .orElse(null);


        //error thrown if null
        Channel channel = device.getChannels().stream()
                .filter(channel1 -> channelNumber == channel1.getChannelNumber())
                .findAny()
                .orElse(null);

        return channel.getType();
    }

    public VirtualChannel getVirtualChannelByType(String type)
    {

        VirtualChannel virtualChannel = this.getVirtualChannelList().stream()
                .filter(virtualChannel1 -> virtualChannel1.getType().equals(type))
                .findAny()
                .orElse(null);
        return virtualChannel;
    }

    public VirtualObject getVirtualDeviceByMacAndChannelNumber(String mac, long channelNumber)
    {
        //error thrown
//        Device device = this.getDeviceList().stream()
//                .filter(device1 -> mac.equals(device1.getMacAdr()))
//                .findAny()
//                .orElse(null);
//
//        //error thrown
//        Channel channel = device.getChannels().stream()
//                .filter(channel1 ->  channelNumber == channel1.getChannelNumber())
//                .findAny()
//                .orElse(null);

        VirtualObject virtualObject = this.getVirtualObjectList().stream()
                .filter(virtualDevice1 -> virtualDevice1.getMac() == mac && virtualDevice1.getChannelNumber() == channelNumber)
                .findAny()
                .orElse(null);

        return virtualObject;

    }

    public boolean isDevicePresent(Device device)
    {
        for(Device device1: this.deviceList)
        {
            if(device1.getMacAdr().equals(device.getMacAdr()))
                return true;
        }
        return false;
    }

    public void addChannel (Channel channel)
    {
        this.addChannel(channel);
    }
    public void addChannelsFromChannelList(List<Channel> channelList)
    {
        this.channelList.addAll(channelList);
    }

    public void addVirtualChannelsFromChannelList(List<Channel> channelList)
    {
       for(Channel channel: channelList)
       {
           boolean apperance = false;
           for(VirtualChannel virtualChannel: this.virtualChannelList)
           {
                if(virtualChannel.getType().equals(channel.getType()))
                    apperance = true;
           }
           if(!apperance)
           {
               switch (channel.getType())
               {
                   case "Sensor":
                   {
                        VirtualChannel virtualChannel = new TempSensorChannel("Sensor");
                        this.virtualChannelList.add(virtualChannel);
                   }
               }

           }

       }
    }

    public void addDevice(Device device){
        this.deviceList.add(device);
    }

    public Device getDeviceByMac(String mac) {
        for(Device device: this.deviceList)
        {
            if(device.getMacAdr().equals(mac))
                return device;
        }
        return null;
    }

}
