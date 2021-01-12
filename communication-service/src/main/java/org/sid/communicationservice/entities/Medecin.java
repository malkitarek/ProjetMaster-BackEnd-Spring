package org.sid.communicationservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class Medecin {
    private Long id;
    private String nom;
    private String prenom;
    private Service service;
}
