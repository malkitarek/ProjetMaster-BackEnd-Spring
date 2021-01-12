package org.sid.suivipatientservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
public class Medecin {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long numeroBureau;
    private Long numeroEtage;
    private Long numeroIdentite;
    private String nom;
    private String prenom;
    private String adresse;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateNaissance;
    private Long appUserId;
    @Transient
    private AppUser appUser;
    @ManyToOne
    private Service service;
    @OneToOne(mappedBy = "chefservice",fetch = FetchType.LAZY)
    private Service grade;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Patient> patients;
    @OneToMany(mappedBy = "medecin")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Consultation> consultation;
}
