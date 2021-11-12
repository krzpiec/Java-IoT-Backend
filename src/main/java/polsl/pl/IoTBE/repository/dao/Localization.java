package polsl.pl.IoTBE.repository.dao;

import lombok.Data;

import javax.persistence.*;



@Data
@Entity
@Table(name = "localization")
public class Localization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long localizationId;

    @Column
    private String Description;

}
