package org.sid.communicationservice.service;

import org.sid.communicationservice.entities.Medecin;
import org.sid.communicationservice.entities.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "suivi-patient-service")
public interface MedPatService {
    @GetMapping("/medecinss/{id}")
    public Medecin findMedecinById(@PathVariable(value = "id") Long medecinId);

    @GetMapping("/patients2/{id}")
    public Patient findPatient2ById(@PathVariable("id") Long id);
}
