package polsl.pl.IoTBE.message.channel;

import org.springframework.beans.factory.annotation.Autowired;
import polsl.pl.IoTBE.domain.VirtualObject;
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

       virtualDevice.changeTemperature(10);
       return true;
    }


    public void sendGetSignalToMqtt(VirtualObject virtualObject)
    {
        String topic = virtualObject.getTopicPrefix() + "get";
        String payload = "1";
        mqttController.publish(topic, payload);

    }
    public void sendSetSignalToMqtt(VirtualObject virtualObject)
    {
        String topic = virtualObject.getTopicPrefix() + "set";
        String payload = "1";
        mqttController.publish(topic, payload);

    }

}
