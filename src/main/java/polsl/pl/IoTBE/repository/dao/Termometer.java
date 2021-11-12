package polsl.pl.IoTBE.repository.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
@Table(name = "termometer")
public class Termometer {


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "localization_id", referencedColumnName = "id")
    private Localization localization;

    @OneToMany(mappedBy="termometer")
    private List<TemperatureHistory> temperatureHistories;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long termometerId;

    @Column
    private int value;

    @Column
    private String unit;


}
