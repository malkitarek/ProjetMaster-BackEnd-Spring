package org.sid.communicationservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idMedecin;
    private Long idPatient;
    private String contenu;
    private String type;
    private String url;
    private String fromQui;
    private Date created;
    private Date readed;
    @Transient
    private Medecin medecin;
    @Transient
    private Patient patient;
}
