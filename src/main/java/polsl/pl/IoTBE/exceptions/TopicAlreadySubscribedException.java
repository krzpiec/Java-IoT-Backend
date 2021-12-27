package polsl.pl.IoTBE.exceptions;

public class TopicAlreadySubscribedException extends RuntimeException{

    public TopicAlreadySubscribedException(String message){
        super(message);
    }
}
