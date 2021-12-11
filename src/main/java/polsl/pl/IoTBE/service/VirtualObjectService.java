package polsl.pl.IoTBE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polsl.pl.IoTBE.domain.VirtualObject;
import polsl.pl.IoTBE.storage.StorageMenager;
import polsl.pl.IoTBE.validators.NewDeviceValidator;

@Service
public class VirtualObjectService {

    @Autowired
    StorageMenager storageMenager;
    @Autowired
    NewDeviceValidator newDeviceValidator;

    public boolean checkIfExists(String mac, long channelNumber){
        return newDeviceValidator.checkIfVirtualObjectExistsByMacAndChannelNumber(mac, channelNumber);
    }
}
