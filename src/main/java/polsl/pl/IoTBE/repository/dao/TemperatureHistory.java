package polsl.pl.IoTBE.repository.dao;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;


@Data
@Entity
@Table(name = "temperature_history")
public class TemperatureHistory {

    @ManyToOne
    @JoinColumn(name="termometer_id", nullable=false)
    private Termometer termometer;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long termometerHistoryId;
    @Column
    private int value;
    private Timestamp measureTime;


}
