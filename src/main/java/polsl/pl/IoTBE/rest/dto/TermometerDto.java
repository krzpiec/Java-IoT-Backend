package polsl.pl.IoTBE.rest.dto;

import lombok.Data;
import polsl.pl.IoTBE.repository.dao.Channel;
import polsl.pl.IoTBE.repository.dao.Localization;

import java.util.List;

@Data
public class TermometerDto {
    private TermometerDescriptionDto termometerDescriptionDto;
    private List<TemperatureHistoryDto> temperatureHistoryDto;
    private ChannelDto channelDto;

}
