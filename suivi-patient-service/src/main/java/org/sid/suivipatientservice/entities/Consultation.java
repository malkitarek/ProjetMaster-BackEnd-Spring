package org.sid.suivipatientservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Consultation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;
    @Lob
    private String rapport;
    @Lob
    private String rapporte;
    @OneToOne(cascade = CascadeType.ALL)
    private Traitement traitement;
    @ManyToOne
    private Medecin medecin;
    @ManyToOne
    private Patient patient;
}
