package org.sid.deviceservice.service;

import org.sid.deviceservice.entities.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

@FeignClient(name = "suivi-patient-service")
public interface PatientService {
    @GetMapping("/membres/{idMed}")
    public Collection<Patient> listMembres(@PathVariable Long idMed);
    @GetMapping("/patients/{id}")
    public Patient findPatientById(@PathVariable("id") Long id);
    @GetMapping("/patientchanel2/{numeroIden}")
    public Patient getPatient2(@PathVariable Long numeroIden);

}
