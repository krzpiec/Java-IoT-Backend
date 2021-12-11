package polsl.pl.IoTBE.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.stereotype.Component;
import polsl.pl.IoTBE.common.MqttConfigValues;
import polsl.pl.IoTBE.message.handler.MqttMessageHandler;
import polsl.pl.IoTBE.repository.dao.Channel;
import polsl.pl.IoTBE.repository.dao.Device;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ConfigHandler {

    @Autowired
    MqttPahoMessageDrivenChannelAdapter adapter;
    @Autowired
    MqttMessageHandler mqttMessageHandler;

    public JSONObject getConfig() {

        JSONObject response = null;
        long start = System.currentTimeMillis();
        long end = start + MqttConfigValues.waitForConfigResponseTimeSeconds * 1000;
        while (System.currentTimeMillis() < end && response == null) {
            response = mqttMessageHandler.getJsonObject();
        }
        if(response != null){
            mqttMessageHandler.setJsonObject(null);
        }
        return response;
    }

    public List<Channel> getChannelListFromConfigJson(Device device) throws JSONException {
        JSONObject jsonObject = this.getConfig();
        JSONArray jsonArray = jsonObject.getJSONArray(MqttConfigValues.configChannelName);
        List<Channel> channelList = new ArrayList<>();


        for(int i=0; i<jsonArray.length(); i++)
        {
            Channel channel = new Channel();
            String  channelNumberString = jsonArray.optJSONObject(i).get(MqttConfigValues.configPortName).toString();
            channel.setChannelNumber(Integer.parseInt(channelNumberString));
            channel.setType(jsonArray.optJSONObject(i).get(MqttConfigValues.configTypeName).toString());
            channel.setDevice(null);
            channelList.add(channel);
        }

        return channelList;
    }

    public void removeTopic(String topic){
        this.adapter.removeTopic(topic);
    }

    public boolean subscribeToGetConfigTopic(Device device)
    {
        String topic = device.getMacAdr() + MqttConfigValues.configSuffix + MqttConfigValues.sendRequestSuffix;
        String[] topics= this.adapter.getTopic();
        boolean contains = Arrays.asList(topics).contains(topic);
        if(!contains) {
            this.adapter.addTopic(topic);
        }
        return contains;
    }



}
