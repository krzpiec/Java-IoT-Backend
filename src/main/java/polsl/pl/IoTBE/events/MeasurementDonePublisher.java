package polsl.pl.IoTBE.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MeasurementDonePublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(String message){
        applicationEventPublisher.publishEvent(new MeasurementDoneEvent<String>(message));
    }
}
