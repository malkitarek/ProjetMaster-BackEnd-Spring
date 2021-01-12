package org.sid.suivipatientservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
//@DynamicUpdate
@Data @NoArgsConstructor @AllArgsConstructor
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long numeroIdentite;
    private String nom;
    private String prenom;
    private String sexe;
    private Long numeroAssurance;
    private String adresse;
    private Date dateNaissance;
    private Long appUserId;
    @ManyToMany(mappedBy = "patients",fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Medecin> medecins;
    @OneToOne(cascade = CascadeType.ALL)
    private DossierMedecal dossierMedecal;
    @OneToMany(mappedBy = "patient")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Consultation> consultation;
    @Transient
    private AppUser appUser;
}
