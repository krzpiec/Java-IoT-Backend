package polsl.pl.IoTBE.repository.dao;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "device")
public class Device {

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "device_device_id")
    private List<Channel> channels;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long deviceId;

    @Column
    private String friendlyName;

    @Column
    private String description;

    @Column
    private Timestamp createTime;

    @Column
    private String macAdr;

}
