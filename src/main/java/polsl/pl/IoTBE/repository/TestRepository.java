package polsl.pl.IoTBE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import polsl.pl.IoTBE.repository.dao.TestEntity;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {

    @Override
    TestEntity save(TestEntity testEntity);

}
