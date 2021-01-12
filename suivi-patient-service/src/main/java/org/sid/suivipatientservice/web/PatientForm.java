package org.sid.suivipatientservice.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Collection;

@Data @AllArgsConstructor @NoArgsConstructor
public class PatientForm {
    private Long idMed;
    private Long numeroIdentite;
    private String nom;
    private String prenom;
    private String email;
    private String phone;
    private String adresse;
    private Date dateNaissance;
    private double taille;
    private String password;
    private double poid;
    private String groupeSanguin;
    private String sexe;
    private Long numeroAssurance;
    private Collection<String> antecedentsFamiliaux;
    private Collection<String> maladies;
    private Collection<String> habitudesToxiques;
    private Long AppUserId;
}
