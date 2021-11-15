package polsl.pl.IoTBE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import polsl.pl.IoTBE.repository.dao.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Override
    Device save(Device device);

}
