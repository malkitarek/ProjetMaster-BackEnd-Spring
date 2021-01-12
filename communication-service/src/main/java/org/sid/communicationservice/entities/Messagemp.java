package org.sid.communicationservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Messagemp {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contenu;
    private Long idPatient;
    private Long idMedecin;
    private String fromQui;
    private Date created;
    private Date readed;
    @Transient
    private Patient patient;
    @Transient
    private Medecin medecin;
}
