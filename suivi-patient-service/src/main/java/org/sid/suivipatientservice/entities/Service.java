package org.sid.suivipatientservice.entities;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
@Entity
 @Data @NoArgsConstructor @AllArgsConstructor
public class Service {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String nomCHU;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //@JsonFilter("PersonAddressFilter")
    private Medecin chefservice;

    @OneToMany(mappedBy = "service")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Medecin> medecins;
}
