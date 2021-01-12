package org.sid.suivipatientservice.service;

import org.sid.suivipatientservice.dao.MedecinRepository;
import org.sid.suivipatientservice.dao.PatientRepository;
import org.sid.suivipatientservice.entities.AppUser;
import org.sid.suivipatientservice.entities.DossierMedecal;
import org.sid.suivipatientservice.entities.Medecin;
import org.sid.suivipatientservice.entities.Patient;
import org.sid.suivipatientservice.web.PatientForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
public class SuiviServiceImpl implements SuiviService {
    @Autowired
    MedecinRepository medecinRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    CompteService compteService;
    @Override
    public void addPatientToMaedecin(Long IdentiteMedecin, Long IdentitePAtient) {
          Medecin medecin=medecinRepository.findByNumeroIdentite(IdentiteMedecin);
          Patient patient=patientRepository.findByNumeroIdentite(IdentitePAtient);
          medecin.getPatients().add(patient);
//          patient.getMedecins().add(medecin);
          medecinRepository.save(medecin);

    }

    @Override
    public void suppPatientToMaedecin(Long IdentiteMedecin, Long IdentitePAtient) {
        Medecin medecin=medecinRepository.findByNumeroIdentite(IdentiteMedecin);
        Patient patient=patientRepository.findByNumeroIdentite(IdentitePAtient);
        medecin.getPatients().remove(patient);
    }

    @Override
    public Collection<Patient> MembreOfMed(Long idMed) {
        Medecin med=medecinRepository.findById(idMed).get();
        return med.getPatients();
    }

    @Override
    public boolean isMembre(Long idPat, Long idMed) {
        AtomicInteger x= new AtomicInteger();
        Medecin med=medecinRepository.findById(idMed).get();
        med.getPatients().forEach(p->{
            if(p.getId()==idPat) {
                x.set(1);
            }
        });
        if(x.get()==1)return true;
        else return false;
    }

    @Override
    @Transactional
    public Patient addPatient(PatientForm patientForm) {

        AppUser compte = compteService.findCompteByEmail(patientForm.getEmail());
        Patient patientIdent=patientRepository.findByNumeroIdentite(patientForm.getNumeroIdentite());
        Patient patientAss=patientRepository.findByNumeroAssurance(patientForm.getNumeroAssurance());
        if (compte != null) throw new RuntimeException("Email is already exist");
        if (patientIdent != null) throw new RuntimeException("Numero d'identite is already exist");
        if (patientAss != null) throw new RuntimeException("Numero d'assurance is already exist");
        Patient patient = new Patient();
        AppUser user = new AppUser();
        user.setEmail(patientForm.getEmail());
        user.setPhone(patientForm.getPhone());
        user.setRole("PATIENT");

        compteService.add(user);

        patient.setNumeroIdentite(patientForm.getNumeroIdentite());
        patient.setNom(patientForm.getNom());
        patient.setPrenom(patientForm.getPrenom());
        patient.setAppUserId(compteService.findCompteByEmail(patientForm.getEmail()).getId());
        patient.setDateNaissance(patientForm.getDateNaissance());
        patient.setAdresse(patientForm.getAdresse());
        patient.setSexe(patientForm.getSexe());
        patient.setNumeroAssurance(patientForm.getNumeroAssurance());
        patient.setDossierMedecal(new DossierMedecal(null, new Date(System.currentTimeMillis() + 3600 * 1000),
                new Date(System.currentTimeMillis() + 3600 * 1000), patientForm.getTaille(), patientForm.getPoid(),
                patientForm.getGroupeSanguin(), patientForm.getAntecedentsFamiliaux(),patientForm.getMaladies(),
                patientForm.getHabitudesToxiques(),null));


        if(patientForm.getIdMed()!=null){
            Medecin medecin = medecinRepository.findById(patientForm.getIdMed()).get();
            patientRepository.save(patient);
            addPatientToMaedecin(medecin.getNumeroIdentite(), patientForm.getNumeroIdentite());
        }
        else{
            patientRepository.save(patient);
        }

        return patient;
    }

}
