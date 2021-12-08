package polsl.pl.IoTBE.repository.dao;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "device")
public class Device {

    @OneToMany
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
