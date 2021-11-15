package polsl.pl.IoTBE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import polsl.pl.IoTBE.repository.dao.Termometer;

@Repository
public interface TermometerRepository extends JpaRepository<Termometer, Long> {

    @Override
    Termometer save(Termometer termometer);

}
