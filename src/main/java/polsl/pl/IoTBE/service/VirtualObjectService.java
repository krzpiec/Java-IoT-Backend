package polsl.pl.IoTBE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import polsl.pl.IoTBE.domain.VirtualObject;
import polsl.pl.IoTBE.exceptions.ChannelTakenException;
import polsl.pl.IoTBE.exceptions.NoChannelForGivenMacException;
import polsl.pl.IoTBE.exceptions.NoDeviceWithGivenMacException;
import polsl.pl.IoTBE.mapper.VirtualObjectMapper;
import polsl.pl.IoTBE.rest.dto.VirtualObjectDto;
import polsl.pl.IoTBE.storage.StorageMenager;
import polsl.pl.IoTBE.validators.NewDeviceValidator;
import polsl.pl.IoTBE.validators.NewVirtualObjectValidator;

@Service
public class VirtualObjectService {

    @Autowired
    StorageMenager storageMenager;
    @Autowired
    NewVirtualObjectValidator newVirtualObjectValidator;
    @Autowired
    VirtualObjectMapper virtualObjectMapper;


    public boolean checkPassedMacAndChannelNumber(String mac, long channelNumber){
        if(!newVirtualObjectValidator.checkPassedMac(mac)){
            throw new NoDeviceWithGivenMacException(mac);
        }

        if(!newVirtualObjectValidator.checkPassedChannelWithMac(mac, channelNumber)){
            throw new NoChannelForGivenMacException(mac, channelNumber);
        }

        VirtualObject virtualObject = newVirtualObjectValidator.checkIfChannelIsTakenByVirtualObject(mac, channelNumber);

        if(virtualObject != null){
            throw new ChannelTakenException("Channel taken by: ", virtualObjectMapper.virtualObjectToVirtualObjectDto(virtualObject));
        }

        return true;
    }
}
