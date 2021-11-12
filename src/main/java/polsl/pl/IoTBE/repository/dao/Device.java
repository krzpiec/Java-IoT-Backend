package polsl.pl.IoTBE.repository.dao;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Table(name = "device")
public class Device {

    @OneToMany (mappedBy="device")
    private List<Channel> channels;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
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
