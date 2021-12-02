package polsl.pl.IoTBE.repository.dao;

import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.sql.Timestamp;


@Data
@Entity
@Table(name = "temperature_history")
public class TemperatureHistory {

    @ManyToOne
    @JoinColumn(name = "termometer_termometer_id")
    @Nullable
    private Termometer termometer;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long termometerHistoryId;

    @Column
    private int value;
    @Column
    private Timestamp measureTime;



}
