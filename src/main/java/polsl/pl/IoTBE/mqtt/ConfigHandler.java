package polsl.pl.IoTBE.mqtt;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import polsl.pl.IoTBE.message.handler.MqttMessageHandler;
import polsl.pl.IoTBE.repository.dao.Channel;
import polsl.pl.IoTBE.repository.dao.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

@Component
public class ConfigHandler {

    @Autowired
    MqttPahoMessageDrivenChannelAdapter adapter;
    @Autowired
    MqttMessageHandler mqttMessageHandler;

    private JSONObject getConfig() {

        JSONObject response = null;
        long start = System.currentTimeMillis();
        long end = start + 20 * 1000;
        while (System.currentTimeMillis() < end && response == null) {
            response = mqttMessageHandler.getJsonObject();
        }
        System.out.println("elo");
        return response;
    }

    public List<Channel> getChannelListFromConfigJson(Device device) throws JSONException {
        JSONObject jsonObject = this.getConfig();
        JSONArray jsonArray = jsonObject.getJSONArray("channels");
        List<Channel> channelList = new ArrayList<>();


        for(int i=0; i<jsonArray.length(); i++) // nie da sie foreachem
        {
            Channel channel = new Channel();
            String  channelNumberString = jsonArray.optJSONObject(i).get("port").toString();
            channel.setChannelNumber(Integer.parseInt(channelNumberString));
            channel.setType(jsonArray.optJSONObject(i).get("type").toString());
            channel.setDevice(null);
            channelList.add(channel);
        }

        return channelList;
    }

    public void addConfigGetTopic(Device device)
    {
        String topic = device.getMacAdr() + "/" + "config";
        this.adapter.addTopic(topic);
    }



}
