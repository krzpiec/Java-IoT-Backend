package polsl.pl.IoTBE.rest.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class DeviceDto {

    private DeviceDescriptionDto deviceDescription;
    private List<ChannelDto> channelDtoList;
    private Timestamp createTime;



}
