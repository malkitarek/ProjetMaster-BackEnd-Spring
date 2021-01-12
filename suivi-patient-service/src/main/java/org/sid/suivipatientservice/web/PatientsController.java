package org.sid.suivipatientservice.web;

import org.sid.suivipatientservice.dao.*;
import org.sid.suivipatientservice.entities.*;
import org.sid.suivipatientservice.service.CompteService;
import org.sid.suivipatientservice.service.SuiviService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
public class PatientsController {
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    MedecinRepository medecinRepository;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    CompteService compteService;
    @Autowired
    SuiviService suiviService;
    @Autowired
    DossierMedecalRepository dossierMedecalRepository;
    @Autowired
    ConsultationRepository consultationRepository;
    @Autowired
    TraitementRepositoy traitementRepositoy;

    @GetMapping("/patients")
    public Collection<Patient> list() {

        Collection<Patient> patients = patientRepository.findAll();
        patients.forEach(m -> {
            m.setAppUser(compteService.findCompteById(m.getAppUserId()));
        });
        return patientRepository.findAll();
    }


    @GetMapping("/patients/{idPat}/{idMed}")
    public Patient getPat(@PathVariable("idPat") Long idPat, @PathVariable("idMed") Long idMed) {

        if (!suiviService.isMembre(idPat, idMed)) throw new RuntimeException("Vous n'êtes pas autorisé");
        Patient patient = patientRepository.findById(idPat).get();
        patient.setAppUser(compteService.findCompteById(patient.getAppUserId()));

        return patient;
    }

    @GetMapping("/patients/{id}")
    public Patient findPatientById(@PathVariable("id") Long id) {

        Patient patient = patientRepository.findById(id).get();
        patient.setAppUser(compteService.findCompteById(patient.getAppUserId()));

        return patient;
    }

    @GetMapping("/membres/{idMed}")
    public Collection<Patient> listMembres(@PathVariable Long idMed) {

        Collection<Patient> patients = suiviService.MembreOfMed(idMed);
        patients.forEach(m -> {
            m.setAppUser(compteService.findCompteById(m.getAppUserId()));
        });
        return patients;
    }

    @GetMapping("/affilier/{idPat}/{idMed}")
    public void listMembres(@PathVariable("idPat") Long idPat, @PathVariable("idMed") Long idMed) {

        Medecin medecin = medecinRepository.findById(idMed).get();
        Patient patient = patientRepository.findById(idPat).get();
        suiviService.addPatientToMaedecin(medecin.getNumeroIdentite(), patient.getNumeroIdentite());
    }

    @GetMapping("/supprimer/{idPat}/{idMed}")
    public void supp(@PathVariable("idPat") Long idPat, @PathVariable("idMed") Long idMed) {

        Medecin medecin = medecinRepository.findById(idMed).get();
        Patient patient = patientRepository.findById(idPat).get();
        suiviService.suppPatientToMaedecin(medecin.getNumeroIdentite(), patient.getNumeroIdentite());
    }

    @PostMapping("/patients")
    @Transactional
    public Patient save(@RequestBody PatientForm patientForm) {

        Patient patient=suiviService.addPatient(patientForm);
        return patient;
    }

    @PutMapping("/patients/{id}")
    public ResponseEntity<Patient> updatePatient(@RequestBody PatientForm patientForm, @PathVariable(value = "id") Long patientId) throws ResourceNotFoundException {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Medecin not found for this id :: " + patientId));


        AppUser user = compteService.findCompteById(patient.getAppUserId());
        Patient patientVerf=patientRepository.findById(patientId).get();

        if (!user.getEmail().equals(patientForm.getEmail())) {
            AppUser u = compteService.findCompteByEmail(patientForm.getEmail());
            if (u != null) {
                throw new RuntimeException("Email is already exist");
            }
        }

        if (!patientVerf.getNumeroIdentite().equals(patientForm.getNumeroIdentite())) {
            Patient patientIdent=patientRepository.findByNumeroIdentite(patientForm.getNumeroIdentite());

            if (patientIdent != null) {
                throw new RuntimeException("Numero d'identite is already exist");
            }

        }

        if (!patientVerf.getNumeroAssurance().equals(patientForm.getNumeroAssurance())) {

            Patient patientAss=patientRepository.findByNumeroAssurance(patientForm.getNumeroAssurance());

            if (patientAss != null) {
                throw new RuntimeException("Numero d'assurance is already exist");
            }
        }

        user.setPhone(patientForm.getPhone());
        user.setEmail(patientForm.getEmail());
        compteService.updaat(user,patient.getAppUserId());

        patient.setNumeroIdentite(patientForm.getNumeroIdentite());
        patient.setNom(patientForm.getNom());
        patient.setPrenom(patientForm.getPrenom());
       // patient.setAppUserId(compteService.findCompteByEmail(patientForm.getEmail()).getId());
        patient.setDateNaissance(patientForm.getDateNaissance());
        patient.setAdresse(patientForm.getAdresse());
        patient.setSexe(patientForm.getSexe());
        patient.setNumeroAssurance(patientForm.getNumeroAssurance());
        if (patient.getDossierMedecal() != null) {
            DossierMedecal medecal = dossierMedecalRepository.findById(patient.getDossierMedecal().getId()).get();
            //new DossierMedecal(null,null,new Date(),patientForm.getTaille(),patientForm.getPoid(),patientForm.getAntecedentsFamiliaux())
            medecal.setTaille(patientForm.getTaille());
            medecal.setPoid(patientForm.getPoid());
            medecal.setDateModification(new Date(System.currentTimeMillis() + 3600 * 1000));
            medecal.setAntecedentsFamiliaux(patientForm.getAntecedentsFamiliaux());
            medecal.setGroupeSanguin(patientForm.getGroupeSanguin());
            medecal.setMaladies(patientForm.getMaladies());
            medecal.setHabitudesToxiques(patientForm.getHabitudesToxiques());
            patient.setDossierMedecal(medecal);
        } else {
            patient.setDossierMedecal(new DossierMedecal(null, new Date(System.currentTimeMillis() + 3600 * 1000), new Date(System.currentTimeMillis() + 3600 * 1000),
                    patientForm.getTaille(), patientForm.getPoid(),
                    patientForm.getGroupeSanguin(),patientForm.getAntecedentsFamiliaux(),patientForm.getMaladies(),
                    patientForm.getHabitudesToxiques(),null));
        }


        patientRepository.save(patient);


        return ResponseEntity.ok(patient);
    }

    @GetMapping("/patient/{email}/{idPat}")
    public boolean ser(@PathVariable("email") String email, @PathVariable("idPat") Long idPat) {

        if (idPat != -1) {
            Patient patient = patientRepository.findById(idPat).get();
            AppUser user = compteService.findCompteById(patient.getAppUserId());

            if (!user.getEmail().equals(email)) {
                AppUser u = compteService.findCompteByEmail(email);
                if (u != null) {
                    return true;
                }
            }

            return false;
        } else {
            AppUser u = compteService.findCompteByEmail(email);
            if (u != null) {
                return true;
            }
        }

        return false;
    }

    @GetMapping("/patientNIden/{numeroIdentite}/{idPat}")
    public boolean validateIdntite(@PathVariable("numeroIdentite") Long numeroIdentite, @PathVariable("idPat") Long idPat) {

        if (idPat != -1) {
            Patient patient = patientRepository.findById(idPat).get();
            //AppUser user = compteService.findCompteById(patient.getAppUserId());

            if (!patient.getNumeroIdentite().equals(numeroIdentite)) {
                Patient u = patientRepository.findByNumeroIdentite(numeroIdentite);
                if (u != null) {
                    return true;
                }
            }

            return false;
        } else {
            Patient u = patientRepository.findByNumeroIdentite(numeroIdentite);
            if (u != null) {
                return true;
            }
        }

        return false;
    }


    @GetMapping("/patientNAssu/{numeroAssurance}/{idPat}")
    public boolean validateAssurance(@PathVariable("numeroAssurance") Long numeroAssurance, @PathVariable("idPat") Long idPat) {

        if (idPat != -1) {
            Patient patient = patientRepository.findById(idPat).get();
            //AppUser user = compteService.findCompteById(patient.getAppUserId());
             if(patient.getNumeroAssurance()==null)patient.setNumeroAssurance(0L);
            if (!patient.getNumeroAssurance().equals(numeroAssurance)) {
                Patient u = patientRepository.findByNumeroAssurance(numeroAssurance);
                if (u != null) {
                    return true;
                }
            }

            return false;
        } else {
            Patient u = patientRepository.findByNumeroAssurance(numeroAssurance);
            if (u != null) {
                return true;
            }
        }

        return false;
    }

    @GetMapping("/medecinsTraitants/{idPat}")
    public Collection<Medecin> getMed(@PathVariable Long idPat) {
        return patientRepository.findById(idPat).get().getMedecins();
    }

    @GetMapping("/patients2/{id}")
    public Patient findPatient2ById(@PathVariable("id") Long id) {

        Patient patient = patientRepository.findById(id).get();
        return patient;

    }

    @GetMapping("/patient/{email}")
    public Patient getPatientByEmail(@PathVariable(value = "email") String email){
        Patient patient=patientRepository.findByAppUserId(compteService.findCompteByEmail(email).getId());
        patient.setAppUser(compteService.findCompteByEmail(email));
        if(patient==null) throw new RuntimeException("Medecin n'est plus exister");
        return patient;
    }
    @GetMapping("/patientchanel/{idPat}")
    public Patient getPatient(@PathVariable Long idPat){
        Patient patient=patientRepository.findById(idPat).get();
        patient.setAppUser(compteService.findCompteById(patient.getAppUserId()));
        return patient;
    }

    @GetMapping("/patientchanel2/{numeroIden}")
    public Patient getPatient2(@PathVariable Long numeroIden){
        return patientRepository.findByNumeroIdentite(numeroIden);
    }
    @PutMapping("/profileEditPat/{idPat}")
    public Patient editProfile(@RequestBody PatientForm patientForm,@PathVariable Long idPat){


        if(!compteService.verife(patientForm.getPassword(),patientForm.getEmail())){

            throw new RuntimeException("Invalid password");

        }



        Patient patient=patientRepository.findById(idPat).get();

        AppUser user=compteService.findCompteById(patient.getAppUserId());
        user.setPhone(patientForm.getPhone());
        compteService.updaat(user,patient.getAppUserId());


        patient.setNom(patientForm.getNom());
        patient.setPrenom(patientForm.getPrenom());
        patient.setAdresse(patientForm.getAdresse());
        patient.setDateNaissance(patientForm.getDateNaissance());
        return patientRepository.save(patient);
    }

}

