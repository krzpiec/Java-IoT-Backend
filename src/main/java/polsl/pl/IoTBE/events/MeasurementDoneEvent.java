package polsl.pl.IoTBE.events;


import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MeasurementDoneEvent<T> extends ApplicationEvent {

    private T data;

    public MeasurementDoneEvent(T event){
        super(event);
        this.data = event;
    }
}
