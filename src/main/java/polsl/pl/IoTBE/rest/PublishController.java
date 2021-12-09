package polsl.pl.IoTBE.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import polsl.pl.IoTBE.domain.VirtualObject;
import polsl.pl.IoTBE.mapper.ChannelMapper;
import polsl.pl.IoTBE.repository.dao.Channel;
import polsl.pl.IoTBE.repository.dao.Device;
import polsl.pl.IoTBE.rest.dto.ChannelDto;
import polsl.pl.IoTBE.rest.dto.DeviceDescriptionDto;
import polsl.pl.IoTBE.rest.dto.DeviceDto;
import polsl.pl.IoTBE.rest.dto.PublishMessageDto;
import polsl.pl.IoTBE.storage.StorageMenager;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PublishController {

    @Autowired
    StorageMenager storageMenager;
    @Autowired
    ChannelMapper channelMapper;
    @GetMapping("/{macAdr}")
    public ResponseEntity<List<ChannelDto>> add(@PathVariable String macAdr) {


        List<Channel> channelList = storageMenager.getChannelsByMac(macAdr);
        List<ChannelDto> channelDtoList = new ArrayList<>();
        channelList.forEach(channel -> {
            channelDtoList.add(channelMapper.channelToChannelDto(channel));
        });
        return ResponseEntity.ok(channelDtoList);

    }

    @PostMapping("/{macAdr}/{channelNumber}/sendToMqtt")
    public ResponseEntity<String> add(@RequestBody PublishMessageDto message, @PathVariable String macAdr, @PathVariable String channelNumber){

        VirtualObject virtualObject = storageMenager.getVirtualObjectByMacAndChannelNumber(macAdr,Long.parseLong(channelNumber));
        virtualObject.getVirtualChannel().sendGetSignalToMqtt(virtualObject.getTopicPrefix(), message.getPayLoad());

        String response = "Message send to topic: " + virtualObject.getTopicPrefix() + " message: " + message.getPayLoad();
        return ResponseEntity.ok(response);
    }
}
