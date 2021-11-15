package polsl.pl.IoTBE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import polsl.pl.IoTBE.repository.dao.Localization;

@Repository
public interface LocalizationRepository extends JpaRepository<Localization, Long> {

    @Override
    Localization save(Localization localization);

}
