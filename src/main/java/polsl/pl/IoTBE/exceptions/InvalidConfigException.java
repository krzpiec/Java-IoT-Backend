package polsl.pl.IoTBE.exceptions;

import lombok.Getter;

@Getter
public class InvalidConfigException extends RuntimeException{

    String where;
    public InvalidConfigException(String where)
    {
        super("Invalid config");
        this.where = where;
    }

}
