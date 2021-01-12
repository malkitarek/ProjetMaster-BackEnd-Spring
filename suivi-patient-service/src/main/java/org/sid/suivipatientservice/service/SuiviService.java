package org.sid.suivipatientservice.service;

import org.sid.suivipatientservice.entities.Medecin;
import org.sid.suivipatientservice.entities.Patient;
import org.sid.suivipatientservice.web.PatientForm;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


public interface SuiviService {
    public void addPatientToMaedecin(Long IdentiteMedecin,Long IdentitePAtient);
    public void suppPatientToMaedecin(Long IdentiteMedecin,Long IdentitePAtient);
    public Collection<Patient> MembreOfMed(Long idMed);
    public boolean isMembre(Long idPat,Long idMed);
    public Patient addPatient(PatientForm patientForm);

}
