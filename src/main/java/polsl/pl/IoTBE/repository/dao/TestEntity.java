package polsl.pl.IoTBE.repository.dao;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "test_entity")
public class TestEntity {

    @Id
    private long id;

    @Column
    private String name;


}
