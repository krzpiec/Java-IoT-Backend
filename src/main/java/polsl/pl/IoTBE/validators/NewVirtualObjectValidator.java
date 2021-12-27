package polsl.pl.IoTBE.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import polsl.pl.IoTBE.domain.VirtualObject;
import polsl.pl.IoTBE.exceptions.ChannelTakenException;
import polsl.pl.IoTBE.exceptions.NoChannelForGivenMacException;
import polsl.pl.IoTBE.exceptions.NoDeviceWithGivenMacException;
import polsl.pl.IoTBE.mapper.VirtualObjectMapper;
import polsl.pl.IoTBE.storage.StorageMenager;

@Component
public class NewVirtualObjectValidator {

    @Autowired
    StorageMenager storageMenager;


    public boolean checkPassedMac(String mac){
        if(!storageMenager.isMacPresent(mac)){
            return false;
        }
        return true;
    }
    public boolean checkPassedChannelWithMac(String mac, long channelNumber){
        if(storageMenager.getChannelByMacAndChannelNumber(mac ,channelNumber) == null){
            return false;
        }
        return true;
    }

    public VirtualObject checkIfChannelIsTakenByVirtualObject(String mac, long channelNumber){

        VirtualObject virtualObject = storageMenager.getVirtualObjectByMacAndChannelNumber(mac, channelNumber);

        return virtualObject;
    }



}
