package polsl.pl.IoTBE.repository.dao;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "channel")
public class Channel {

    @ManyToOne
    @JoinColumn(name = "device_device_id")
    private Device device;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long channelId;

    @Column
    private String type;

    @Column
    private long channelNumber;

}
