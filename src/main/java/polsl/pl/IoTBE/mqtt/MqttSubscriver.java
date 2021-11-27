package polsl.pl.IoTBE.mqtt;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;

@Component
public class MqttSubscriver {

    @Autowired
    MqttPahoMessageDrivenChannelAdapter adapter;
//    @Async
//    CompletableFuture<String> createDocxToPdfAndThenToImage(String targetLocation);
//
//    CompletableFuture<String> future=createDocxToPdfAndThenToImage(targetLocation);
//       return future.get( 10, SECONDS);

    public void addTopic(String topic){
        this.adapter.addTopic(topic);
        System.out.println(this.adapter.getTopic());

    }

}
