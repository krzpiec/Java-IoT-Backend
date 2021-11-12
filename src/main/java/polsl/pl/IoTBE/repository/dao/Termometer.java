package polsl.pl.IoTBE.repository.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "termometer")
public class Termometer {


    @OneToOne(targetEntity = Localization.class)
    private Localization localization;

    @OneToMany(targetEntity = Termometer.class)
    private List<TemperatureHistory> temperatureHistories;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long termometerId;

    @Column
    private int value;

    @Column
    private String unit;


}
