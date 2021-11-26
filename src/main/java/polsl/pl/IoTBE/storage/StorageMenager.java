package polsl.pl.IoTBE.storage;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import polsl.pl.IoTBE.domain.VirtualDevice;
import polsl.pl.IoTBE.message.channel.VirtualChannel;
import polsl.pl.IoTBE.repository.dao.Channel;
import polsl.pl.IoTBE.repository.dao.Device;

import java.util.List;

@Component
@Data
@AllArgsConstructor
public class StorageMenager {

    List<VirtualDevice> virtualDeviceList;
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



    public VirtualDevice getVirtualDeviceByMacAndChannelNumber(String mac, long channelNumber)
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

        VirtualDevice virtualDevice = this.getVirtualDeviceList().stream()
                .filter(virtualDevice1 -> virtualDevice1.getMac() == mac && virtualDevice1.getChannelNumber() == channelNumber)
                .findAny()
                .orElse(null);

        return virtualDevice;

    }

}
