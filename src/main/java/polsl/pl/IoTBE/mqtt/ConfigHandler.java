package polsl.pl.IoTBE.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.stereotype.Component;
import polsl.pl.IoTBE.common.MqttConfigValues;
import polsl.pl.IoTBE.exceptions.InvalidMqttMessageException;
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

    public List<Channel> getChannelListFromConfigJson(Device device){

        List<Channel> channelList = new ArrayList<>();

        try{
            JSONObject jsonObject = this.getConfig();
            JSONArray jsonArray = jsonObject.getJSONArray(MqttConfigValues.configChannelName);



            for(int i=0; i<jsonArray.length(); i++)
            {
                Channel channel = new Channel();
                String  channelNumberString = jsonArray.optJSONObject(i).get(MqttConfigValues.configPortName).toString();
                channel.setChannelNumber(Integer.parseInt(channelNumberString));
                channel.setType(jsonArray.optJSONObject(i).get(MqttConfigValues.configTypeName).toString());
                channel.setDevice(null);
                channelList.add(channel);
            }
        }
        catch(Exception ex){
            throw new InvalidMqttMessageException("Config conversion to channel list");
        }


        return channelList;
    }


    public boolean subscribeToGetConfigTopic(Device device)
    {
        String topic = device.getMacAdr() + MqttConfigValues.configSuffix + MqttConfigValues.receiveRequestSuffix;
        String[] topics= this.adapter.getTopic();
        boolean contains = Arrays.asList(topics).contains(topic);
        if(!contains) {
            this.adapter.addTopic(topic);
        }
        return contains;
    }

    public boolean removeConfigTopic(Device device){
        String topic = device.getMacAdr() + MqttConfigValues.configSuffix + MqttConfigValues.receiveRequestSuffix;
        String[] topics= this.adapter.getTopic();
        boolean contains = Arrays.asList(topics).contains(topic);
        if(!contains) {
            this.adapter.removeTopic(topic);
            return true;
        }
        return false;
    }


}
