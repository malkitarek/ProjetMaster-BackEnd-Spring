package org.sid.deviceservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class Patient {
    private Long id;
    private Long numeroIdentite;
    private String nom;
    private String prenom;
}
