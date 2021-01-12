package org.sid.deviceservice.web;

import javafx.print.Collation;
import org.sid.deviceservice.dao.DonneeRepository;
import org.sid.deviceservice.dao.FeildRepository;
import org.sid.deviceservice.entities.Donnee;
import org.sid.deviceservice.entities.Feild;
import org.sid.deviceservice.entities.Patient;
import org.sid.deviceservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class DonneController {
    @Autowired
    DonneeRepository donneeRepository;
    @Autowired
    FeildRepository feildRepository;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    PatientService patientService;


    @GetMapping("/donne")
    @CrossOrigin("*")
    public void addDonne(@RequestParam("feild_nom") String feild_nom,@RequestParam("valeur") String valeur,
                         @RequestParam("numero_iden") String numeroIdentite ){
       /* List<String> tmp1;
        tmp1= Arrays.asList(temp.split("_"));
        tmp1.forEach(t->{
            donneeRepository.save(new Donnee(null,Double.valueOf(t),new Date(System.currentTimeMillis() + 3600 * 1000),feildRepository.findByNomAndChanel_PatientId("temp",1L)));
        });*/
        Patient patient=patientService.getPatient2(Long.valueOf(numeroIdentite));

       Feild feild=feildRepository.findByNomAndChanel_PatientId(feild_nom,patient.getId());

       if(feild_nom.equals("ECG wave")){
           List<String> ecg;
           ecg= Arrays.asList(valeur.split("_"));
           final int[] x = {100};
           ecg.forEach(t->{

               Date now=new Date();
               now.setTime(now.getTime() - x[0]);
               Donnee donnee= donneeRepository.save(new Donnee(null,Double.valueOf(t),now,feild));
               simpMessagingTemplate.convertAndSend("/topic/donnees",donnee);
               x[0] = x[0] -10;
           });
       }

        else{
            Donnee donnee= donneeRepository.save(new Donnee(null,Double.valueOf(valeur),new Date(),feild));
            simpMessagingTemplate.convertAndSend("/topic/donnees",donnee);
        }

        // return donneeRepository.save(new Donnee(null,temp,new Date(),feildRepository.findByNom("temp")));
    }

    @GetMapping("/donnees")
    public Collection<Donnee> getDonnes(@RequestParam("patient_id") Long idPat,@RequestParam("chanel_id") Long idChanel,@RequestParam("feild_nom") String nomFeild){

        Feild feild=feildRepository.findByChanel_PatientIdAndChanel_IdAndNom(idPat,idChanel,nomFeild);
        Collection<Donnee> donnees=donneeRepository.findByFeild(feild);
        return donnees;

    }
    @GetMapping("/lastDonnees")
    public Collection<Donnee> getLastDonnes(@RequestParam("patient_id") Long idPat,@RequestParam("chanel_id") Long idChanel,@RequestParam("feild_nom") String nomFeild){

        Feild feild=feildRepository.findByChanel_PatientIdAndChanel_IdAndNom(idPat,idChanel,nomFeild);
        Collection<Donnee> donnees=donneeRepository.findByFeildOrderByTimeDesc(feild);
        Set<Integer> set = new HashSet<>(donnees.size());
        donnees.removeIf(d -> !set.add(d.getTime().getHours()));
        return donnees;

    }
    @GetMapping("/feilds")
    public Collection<Feild> getFeilds(@RequestParam("patient_id") Long idPat,@RequestParam("chanel_id") Long idChanel){
        return feildRepository.findByChanel_PatientIdAndChanel_Id(idPat,idChanel);
    }
}
