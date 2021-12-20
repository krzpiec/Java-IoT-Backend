package polsl.pl.IoTBE.exceptions;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class NoResponseFromSensorException extends RuntimeException{

    List<String> topicPrefixes = new ArrayList<>();

    public NoResponseFromSensorException(List<String> topicPrefixes) {
        super();
        topicPrefixes.add(0,"Sensors that didnt send response: ");
        this.topicPrefixes = topicPrefixes;
    }
}
