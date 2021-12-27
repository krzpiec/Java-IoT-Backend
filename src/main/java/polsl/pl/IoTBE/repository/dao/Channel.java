package polsl.pl.IoTBE.repository.dao;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;


@Getter
@Setter
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
