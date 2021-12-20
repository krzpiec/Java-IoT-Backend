package polsl.pl.IoTBE.message.channel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import polsl.pl.IoTBE.common.ChannelTypes;
import polsl.pl.IoTBE.common.MqttConfigValues;
import polsl.pl.IoTBE.domain.VirtualObject;
import polsl.pl.IoTBE.exceptions.WrongPayloadException;
import polsl.pl.IoTBE.mqtt.MqttController;


public class TempSensorChannel extends VirtualChannel<TempSensor>
{
    @Autowired
    MqttController mqttController;

    public TempSensorChannel(String type)
    {
        super(type);

    }

    @Override
    public Boolean executeMessage(String msg, TempSensor virtualDevice) {

        try{
            double readValue = Double.parseDouble(msg);
            virtualDevice.changeTemperature(readValue);
        }
        catch (NumberFormatException ex){
           return false;
        }



       return true;
    }

    @Override
    public void sendGetSignalToMqtt(String topic, String payload)  {
        topic += MqttConfigValues.sendRequestSuffix;
        mqttController.publish(topic, payload);

    }
    public void sendSetSignalToMqtt(VirtualObject virtualObject) throws JSONException {
        String topic = virtualObject.getTopicPrefix() + MqttConfigValues.receiveRequestSuffix;
        String payload = "1";
        mqttController.publish(topic, payload);

    }

}
