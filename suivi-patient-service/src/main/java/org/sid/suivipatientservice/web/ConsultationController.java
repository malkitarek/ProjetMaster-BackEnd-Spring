package org.sid.suivipatientservice.web;

import org.sid.suivipatientservice.dao.*;
import org.sid.suivipatientservice.entities.Consultation;
import org.sid.suivipatientservice.entities.Traitement;
import org.sid.suivipatientservice.service.CompteService;
import org.sid.suivipatientservice.service.SuiviService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

@RestController
public class ConsultationController {
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


    @PostMapping("/consulations")
    public Consultation addConsulation(@RequestBody ConsultationForm consultationForm){



        if(consultationForm.getIdCons()==null){
            Traitement traitement=traitementRepositoy.save(new Traitement(null,consultationForm.getNomTraitement(),
                    consultationForm.getTraitements()));

            return consultationRepository.save(new Consultation(null,new Date(System.currentTimeMillis() + 3600 * 1000)
                    ,consultationForm.getRapport(),consultationForm.getRapporte(),traitement,
                    consultationForm.getMedecin(),consultationForm.getPatient()));
        }
        else {
            Consultation consultation=consultationRepository.findById(consultationForm.getIdCons()).get();
            consultation.setRapporte(consultationForm.getRapporte());
            consultation.setMedecin(consultationForm.getMedecin());
            consultation.setPatient(consultationForm.getPatient());
            Traitement traitement=traitementRepositoy.findById(consultation.getTraitement().getId()).get();
            traitement.setNom(consultationForm.getNomTraitement());
            traitement.setContenu(consultationForm.getTraitements());
            consultation.setTraitement(traitement);
            return consultationRepository.save(consultation);
        }

    }

    @GetMapping("consulations/{idPat}/{idMed}")
    public Collection<Consultation> getConsultations(@PathVariable("idPat") Long idPat, @PathVariable("idMed") Long idMed){
        return consultationRepository.findByPatient_IdAndMedecin_Id(idPat,idMed);
    }
    @DeleteMapping("consulations/{idCons}")
    public void deleteConsultation(@PathVariable("idCons") Long idCons){
        Consultation consultation=consultationRepository.findById(idCons).get();
        consultationRepository.delete(consultation);
    }
}
