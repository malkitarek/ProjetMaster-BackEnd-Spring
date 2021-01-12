package org.sid.suivipatientservice.web;

import lombok.Data;
import org.sid.suivipatientservice.entities.Medecin;
import org.sid.suivipatientservice.entities.Patient;

import java.util.Collection;

@Data
public class ConsultationForm {
    private Long idCons;
    private String nomTraitement;
    private Collection<String> traitements;
    private String rapport;
    private String rapporte;
    private Medecin medecin;
    private Patient patient;
}
