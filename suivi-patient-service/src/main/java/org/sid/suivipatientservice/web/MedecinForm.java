package org.sid.suivipatientservice.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Calendar;

@Data @NoArgsConstructor @AllArgsConstructor
public class MedecinForm {
    private Long numeroIdentite;
    private Long numeroBureau;
    private Long numeroEtage;
    private String nom;
    private String prenom;
    private String email;
    private String phone;
    private String adresse;
    private Date dateNaissance;
    private Long AppUserId;
    private String password;
    private Long service;
}
