package polsl.pl.IoTBE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import polsl.pl.IoTBE.repository.dao.Channel;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    @Override
    Channel save(Channel channel);

}
