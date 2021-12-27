package polsl.pl.IoTBE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import polsl.pl.IoTBE.repository.dao.Channel;
import polsl.pl.IoTBE.repository.dao.Device;

import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    @Override
    Channel save(Channel channel);

    @Override
    List<Channel> findAll();

    Channel findByDeviceAndChannelNumber(Device mac, Long channelNumber);

}
