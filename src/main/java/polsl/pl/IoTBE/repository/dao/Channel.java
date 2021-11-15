package polsl.pl.IoTBE.repository.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
@Table(name = "channel")
public class Channel {

    @ManyToOne
    @JoinColumn(name = "device_device_id")
    @Nullable
    private Device device;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long channelId;

    @Column
    private String type;

    @Column
    private long channelNumber; //port

}
