package polsl.pl.IoTBE.repository.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "termometer")
public class Termometer {

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "localization_localization_id")
    private Localization localization;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "termometer_termometer_id")
    private List<TemperatureHistory> temperatureHistories;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long termometerId;

    @Column
    private int value;

    @Column
    private String unit;

}
