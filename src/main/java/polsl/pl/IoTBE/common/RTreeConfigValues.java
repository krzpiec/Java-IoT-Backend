package polsl.pl.IoTBE.common;


import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RTreeConfigValues {
    public static String rTreeNameSuffix = "NodeRTree";
    public static String visualizationPath = "D:/Inzynierka/mytree.png";
    public static int maxRecordListSize = 10;
}
