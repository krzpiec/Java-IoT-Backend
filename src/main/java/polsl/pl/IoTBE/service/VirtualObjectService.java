package polsl.pl.IoTBE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polsl.pl.IoTBE.domain.VirtualObject;
import polsl.pl.IoTBE.storage.StorageMenager;

@Service
public class VirtualObjectService {

    @Autowired
    StorageMenager storageMenager;

    public boolean checkIfExists(String mac, long channelNumber){
        return storageMenager.checkIfVirtualObjectExistsByMacAndChannelNumber(mac, channelNumber);
    }
}
