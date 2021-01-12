package org.sid.communicationservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class Patient {
    private Long id;
    private String nom;
    private String prenom;
}
