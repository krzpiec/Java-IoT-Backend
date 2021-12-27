package polsl.pl.IoTBE.events;

import lombok.SneakyThrows;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

public class MeasurementDoneEventHandler {

    @SneakyThrows
    @EventListener
    @Async
    public void handleMeasurementDoneEvent(MeasurementDoneEvent<String> event){
        System.out.println(event.getData());
    }
}
