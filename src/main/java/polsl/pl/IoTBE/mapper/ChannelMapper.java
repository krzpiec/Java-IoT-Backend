package polsl.pl.IoTBE.mapper;

import org.mapstruct.Mapper;
import polsl.pl.IoTBE.repository.dao.Channel;
import polsl.pl.IoTBE.rest.dto.ChannelDto;

@Mapper(componentModel = "spring")
public interface ChannelMapper {

    ChannelDto channelToChannelDto(Channel channel);

    //Channel channelDtoToChannel(ChannelDto channelDto);
}
