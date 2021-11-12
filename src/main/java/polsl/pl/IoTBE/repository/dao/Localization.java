package polsl.pl.IoTBE.repository.dao;

import lombok.Data;

import javax.persistence.*;



@Data
@Entity
@Table(name = "localization")
public class Localization {

      @OneToOne(mappedBy = "localization")
     private Termometer termometer;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long localizationId;

    @Column
    private String Description;



}
