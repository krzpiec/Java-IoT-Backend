package polsl.pl.IoTBE.message.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import polsl.pl.IoTBE.domain.VirtualObject;
import polsl.pl.IoTBE.message.channel.VirtualChannel;
import polsl.pl.IoTBE.storage.StorageMenager;


@Component
public class MqttMessageHandler {

    @Autowired
    StorageMenager storageMenager;

    public void resolveMessage(Message<?> message)
    {
        String payLoad = message.getPayload().toString();
        String topic = message.getHeaders().get("mqtt_receivedTopic").toString();

        String[] topicSegments = topic.split("/");
        String MAC = topicSegments[0];
        long channelNumber = Long.parseLong(topicSegments[1]);

        String type = storageMenager.getTypeByMacAndChannelNumber(MAC, channelNumber);
        VirtualObject virtualObject = storageMenager.getVirtualDeviceByMacAndChannelNumber(MAC, channelNumber);
        VirtualChannel virtualChannel = storageMenager.getVirtualChannelByType(type);


        virtualChannel.executeMessage(payLoad, virtualObject);

    }



}
