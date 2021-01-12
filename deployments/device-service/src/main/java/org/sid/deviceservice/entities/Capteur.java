package org.sid.deviceservice.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.Collection;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Capteur {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nom;
    private String image;
    @OneToMany(mappedBy = "capteur")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Chanel> chanels;

}
