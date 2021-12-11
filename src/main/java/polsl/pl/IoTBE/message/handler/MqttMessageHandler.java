package polsl.pl.IoTBE.message.handler;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import polsl.pl.IoTBE.domain.VirtualObject;
import polsl.pl.IoTBE.message.channel.VirtualChannel;
import polsl.pl.IoTBE.storage.StorageMenager;


@Getter
@Setter
@Component
public class MqttMessageHandler {

    private JSONObject jsonObject = null;


    private StorageMenager storageMenager = null;




    public void resolveMessage(Message<?> message) throws JSONException {
        String payLoad = message.getPayload().toString();
        String topic = message.getHeaders().get("mqtt_receivedTopic").toString();

        String[] topicSegments = topic.split("/");
        if(topicSegments[1].equals("config"))
        {
            jsonObject = new JSONObject(payLoad);
            return;
        }

        String MAC = topicSegments[0];
        long channelNumber = Long.parseLong(topicSegments[1]);

        String type = storageMenager.getTypeByMacAndChannelNumber(MAC, channelNumber);
        VirtualObject virtualObject = storageMenager.getVirtualObjectByMacAndChannelNumber(MAC, channelNumber);
        VirtualChannel virtualChannel = storageMenager.getVirtualChannelByType(type);


        virtualChannel.executeMessage(payLoad, virtualObject);

    }



}
