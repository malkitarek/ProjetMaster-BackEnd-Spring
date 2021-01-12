package org.sid.deviceservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Chanel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @ManyToOne
    private Capteur capteur;
    private Long patientId;
    @OneToMany(mappedBy = "chanel")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Feild> feilds;
    @Transient
    private Patient patient;

}
