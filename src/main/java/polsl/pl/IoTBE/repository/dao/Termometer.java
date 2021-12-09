package polsl.pl.IoTBE.repository.dao;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "termometer")
public class Termometer {

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "localization_localization_id")
    private Localization localization;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "termometer_termometer_id")
    private List<TemperatureHistory> temperatureHistories;


    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "channel_channel_id")
    private Channel channel;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long termometerId;


    @Column
    private String unit;


    @Column
    private String description;



}
