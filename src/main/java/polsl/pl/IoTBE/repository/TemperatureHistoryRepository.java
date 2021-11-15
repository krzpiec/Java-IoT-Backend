package polsl.pl.IoTBE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import polsl.pl.IoTBE.repository.dao.TemperatureHistory;

@Repository
public interface TemperatureHistoryRepository extends JpaRepository<TemperatureHistory, Long> {

    @Override
    TemperatureHistory save(TemperatureHistory temperatureHistory);

}
