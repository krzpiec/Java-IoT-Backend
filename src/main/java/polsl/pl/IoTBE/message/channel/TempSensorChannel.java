package polsl.pl.IoTBE.message.channel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import polsl.pl.IoTBE.domain.VirtualDevice;
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


    public void sendGetSignalToMqtt(VirtualDevice virtualDevice)
    {
        String topic = virtualDevice.getTopicPrefix() + "get";
        String payload = "1";
        mqttController.publish(topic, payload);

    }
    public void sendSetSignalToMqtt(VirtualDevice virtualDevice)
    {
        String topic = virtualDevice.getTopicPrefix() + "set";
        String payload = "1";
        mqttController.publish(topic, payload);

    }

}
