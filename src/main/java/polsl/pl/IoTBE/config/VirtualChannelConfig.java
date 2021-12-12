package polsl.pl.IoTBE.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import polsl.pl.IoTBE.common.ChannelTypes;
import polsl.pl.IoTBE.message.channel.TempSensorChannel;

@Configuration
public class VirtualChannelConfig {


    @Bean
    public TempSensorChannel getTempSensorChannel() {

        return new TempSensorChannel(ChannelTypes.Sensor.toString());
    }


}
