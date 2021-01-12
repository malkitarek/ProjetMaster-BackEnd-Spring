package org.sid.suivipatientservice.web;

import org.sid.suivipatientservice.dao.MedecinRepository;
import org.sid.suivipatientservice.dao.PatientRepository;
import org.sid.suivipatientservice.dao.ServiceRepository;
import org.sid.suivipatientservice.entities.AppUser;
import org.sid.suivipatientservice.entities.Medecin;
import org.sid.suivipatientservice.entities.Patient;
import org.sid.suivipatientservice.entities.Service;
import org.sid.suivipatientservice.service.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MedecinController {
    @Autowired
    MedecinRepository medecinRepository;
    @Autowired
    CompteService compteService;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    PatientRepository patientRepository;

    @GetMapping("/medecins")
    public Page<Medecin> listMedP(@RequestParam(defaultValue = "0") int page){
        Collection<Medecin> medecins=medecinRepository.findAll();
        medecins.forEach(m->{
            m.setAppUser(compteService.findCompteById(m.getAppUserId()));
        });

        return medecinRepository.findAll(PageRequest.of(page,2));
    }
    @GetMapping("/medecinsPage")
    public Collection<Medecin> list(){

        Collection<Medecin> medecins=medecinRepository.findAll();
        medecins.forEach(m->{
            m.setAppUser(compteService.findCompteById(m.getAppUserId()));
        });
        return medecinRepository.findAll();
    }

    @GetMapping("/medecins/{id}")
    public ResponseEntity<Medecin> finMedecinById(@PathVariable(value = "id") Long medecinId)
            throws ResourceNotFoundException {
        Medecin medecin = medecinRepository.findById(medecinId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + medecinId));
        medecin.setAppUser(compteService.findCompteById(medecin.getAppUserId()));
        return ResponseEntity.ok().body(medecin);
    }

    @GetMapping("/medecin/{email}")
    public Medecin getMedecinByEmail(@PathVariable(value = "email") String email){
                Medecin medecin=medecinRepository.findByAppUserId(compteService.findCompteByEmail(email).getId());
                if(medecin==null) throw new RuntimeException("Medecin n'est plus exister");
                medecin.setAppUser(compteService.findCompteByEmail(email));
               return medecin;
    }

    @PostMapping("/medecins")
    public Medecin saveMed(@RequestBody MedecinForm medecinForm){

        AppUser compte=compteService.findCompteByEmail(medecinForm.getEmail());
        if(compte != null)   throw new RuntimeException("Email is already exist");
        Medecin medecin=new Medecin();

        AppUser user=new AppUser();

        user.setEmail(medecinForm.getEmail());
        user.setPhone(medecinForm.getPhone());
        user.setRole("MEDECIN");
        compteService.add(user);

        medecin.setNumeroIdentite(medecinForm.getNumeroIdentite());
        medecin.setNumeroBureau(medecinForm.getNumeroBureau());
        medecin.setNumeroEtage(medecinForm.getNumeroEtage());
        medecin.setNom(medecinForm.getNom());
        medecin.setPrenom(medecinForm.getPrenom());
        medecin.setAppUserId(compteService.findCompteByEmail(medecinForm.getEmail()).getId());
        medecin.setDateNaissance(medecinForm.getDateNaissance());
        medecin.setAdresse(medecinForm.getAdresse());
        medecin.setService(serviceRepository.findById(medecinForm.getService()).get());


        return medecinRepository.save(medecin);
    }
    @DeleteMapping("/medecins/{id}")
    public Map<String, Boolean> deleteMedecin(@PathVariable(value = "id") Long medecinId)
            throws ResourceNotFoundException {
        Medecin medecin = medecinRepository.findById(medecinId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + medecinId));

        Service service=serviceRepository.findByChefservice_Id(medecinId);


        medecin.setGrade(null);
        medecinRepository.save(medecin);

        if (service != null) {
            service.setChefservice(null);
            serviceRepository.save(service);
        }




        compteService.deleteCompte(medecin.getAppUserId());
        medecinRepository.delete(medecin);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PutMapping("/medecins/{id}")
    public ResponseEntity<Medecin> updateEmployee(@RequestBody MedecinForm medecinDetails, @PathVariable(value = "id") Long medecinId) throws ResourceNotFoundException {
        Medecin medecin = medecinRepository.findById(medecinId)
                .orElseThrow(() -> new RuntimeException("Medecin not found for this id :: " + medecinId));


        AppUser user=compteService.findCompteById(medecin.getAppUserId());

        if (!user.getEmail().equals(medecinDetails.getEmail())){
            AppUser u=compteService.findCompteByEmail(medecinDetails.getEmail());
            if (u != null) {
                throw new RuntimeException("Email is already exist");
            }}
        user.setEmail(medecinDetails.getEmail());
        user.setPhone(medecinDetails.getPhone());
        compteService.updaat(user,medecin.getAppUserId());

        medecin.setNumeroIdentite(medecinDetails.getNumeroIdentite());
        medecin.setNumeroBureau(medecinDetails.getNumeroBureau());
        medecin.setNumeroEtage(medecinDetails.getNumeroEtage());
        medecin.setNom(medecinDetails.getNom());
        medecin.setPrenom(medecinDetails.getPrenom());
        medecin.setAppUserId(compteService.findCompteByEmail(medecinDetails.getEmail()).getId());
        medecin.setDateNaissance(medecinDetails.getDateNaissance());
        medecin.setAdresse(medecinDetails.getAdresse());
        medecin.setService(serviceRepository.findById(medecinDetails.getService()).get());
        final Medecin updatedMedecin = medecinRepository.save(medecin);
        return ResponseEntity.ok(updatedMedecin);
    }
    @GetMapping("/medecinss/{id}")
    public Medecin findMedecinById(@PathVariable(value = "id") Long medecinId){
        Medecin medecin=medecinRepository.findById(medecinId).get();
        return medecin;
    }

    @PutMapping("/profileEditMed/{idMed}")
    public Medecin editProfile(@RequestBody MedecinForm medecinForm,@PathVariable Long idMed){

        System.out.println("/*********************************************************"+medecinForm.getPassword()+medecinForm.getEmail() +" ********************************/");
        if(!compteService.verife(medecinForm.getPassword(),medecinForm.getEmail())){

            throw new RuntimeException("Invalid password");

        }



        Medecin medecin=medecinRepository.findById(idMed).get();

        AppUser user=compteService.findCompteById(medecin.getAppUserId());
        user.setPhone(medecinForm.getPhone());
        compteService.updaat(user,medecin.getAppUserId());


        medecin.setNom(medecinForm.getNom());
        medecin.setPrenom(medecinForm.getPrenom());
        medecin.setAdresse(medecinForm.getAdresse());
        medecin.setDateNaissance(medecinForm.getDateNaissance());
        return medecinRepository.save(medecin);
    }

}
