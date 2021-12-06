package polsl.pl.IoTBE.service;

import org.springframework.beans.factory.annotation.Autowired;
import polsl.pl.IoTBE.domain.VirtualObject;
import polsl.pl.IoTBE.storage.StorageMenager;

public class VirtualObjectService {

    @Autowired
    StorageMenager storageMenager;

    public boolean checkIfExists(String mac, long channelNumber)
    {
        for(VirtualObject virtualObject: storageMenager.getVirtualObjectList())
        {
            if(virtualObject.getMac().equals(mac) && virtualObject.getChannelNumber() == channelNumber)
                return true;
        }
        return false;
    }
}
