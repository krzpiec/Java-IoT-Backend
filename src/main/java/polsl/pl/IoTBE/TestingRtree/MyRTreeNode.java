package polsl.pl.IoTBE.TestingRtree;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import polsl.pl.IoTBE.domain.VirtualObject;

@AllArgsConstructor
@Getter
@Setter
public class MyRTreeNode {

    VirtualObject virtualObject;

    public String value(){
        return virtualObject.getTopicPrefix();
    }
}
