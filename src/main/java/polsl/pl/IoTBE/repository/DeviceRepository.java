package polsl.pl.IoTBE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import polsl.pl.IoTBE.repository.dao.Device;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

    @Override
    Device save(Device device);

    @Override
    List<Device> findAll();

    Device findByMacAdr(String mac);

    @Override
    Optional<Device> findById(Long id);
}
