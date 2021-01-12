package org.sid.suivipatientservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Collection;
import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class DossierMedecal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateCreation;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateModification;
    private double taille;
    private double poid;
    private String groupeSanguin;
    @ElementCollection
    private Collection<String> antecedentsFamiliaux;
    @ElementCollection
    private Collection<String> maladies;
    @ElementCollection
    private Collection<String> habitudesToxiques;
    @ElementCollection
    private Collection<String> churigicaux;
}
