package polsl.pl.IoTBE.mqtt;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import polsl.pl.IoTBE.message.handler.MqttMessageHandler;
import polsl.pl.IoTBE.storage.StorageMenager;

@Slf4j
@Configuration
@Getter
public class MqttSubscriberConfig {

    MqttPahoMessageDrivenChannelAdapter adapter;

    @Autowired
    MqttMessageHandler mqttMessageHandler;


    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter("tcp://localhost:1883", "testClient",
                        "topic1");

        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        this.adapter = adapter;
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {

            @SneakyThrows
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                mqttMessageHandler.resolveMessage(message);
              // System.out.println(message.getHeaders());
//                log.debug("message arrived: {} ",message.getPayload());
            }

        };
    }

    public void addTopic(String topic){
        this.adapter.addTopic(topic);
        System.out.println(this.adapter.getTopic());

    }
}
