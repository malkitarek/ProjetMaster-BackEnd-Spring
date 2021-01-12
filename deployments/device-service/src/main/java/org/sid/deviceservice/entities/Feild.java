package org.sid.deviceservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Feild {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private double max;
    private double min;
    @ManyToOne
    private Chanel chanel;
    @OneToMany(mappedBy = "feild")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Donnee> donnees;
}
