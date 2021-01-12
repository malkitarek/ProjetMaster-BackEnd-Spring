package org.sid.communicationservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Rendezvous {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private Date dateDebut;
    private Date dateFin;
    private Long idPatient;
    private Long idMedecin;
    private String description;
    private String validation;
    @Transient
    private Medecin medecin;
    @Transient
    private Patient patient;

}
