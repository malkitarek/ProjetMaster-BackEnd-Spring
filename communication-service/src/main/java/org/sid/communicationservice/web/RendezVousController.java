package org.sid.communicationservice.web;

import org.sid.communicationservice.dao.NotificationRepository;
import org.sid.communicationservice.dao.RendezvousRepository;
import org.sid.communicationservice.entities.Medecin;
import org.sid.communicationservice.entities.Notification;
import org.sid.communicationservice.entities.Patient;
import org.sid.communicationservice.entities.Rendezvous;
import org.sid.communicationservice.service.MedPatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

@RestController
public class RendezVousController {
    @Autowired
    RendezvousRepository rendezvousRepository;
    @Autowired
    MedPatService medPatService;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/rendezVous/{idMed}")
    Collection<Rendezvous> getRendez(@PathVariable Long idMed)
    {
        Collection<Notification> notifications=notificationRepository.findByIdMedecinAndFromQuiNotContainsOrderByCreatedDesc(idMed,"MEDECIN");

        notifications.forEach(noti->{
            noti.setReaded(new Date());
            notificationRepository.save(noti);
        });

        simpMessagingTemplate.convertAndSend("/topic/notifications",notifications);
        return rendezvousRepository.findByIdMedecin(idMed);
    }
    @GetMapping("/rendezVousMlast/{idMed}")
    Collection<Rendezvous> getRendezLast(@PathVariable Long idMed)
    {
       Collection<Rendezvous> rendezvous= rendezvousRepository.findByIdMedecinOrderByDateDebutDesc(idMed);

    return rendezvous;
    }

    @PostMapping("/rendezVous")
    Rendezvous saveRendez(@RequestBody Rendezvous rendezvous){
        Medecin medecin=medPatService.findMedecinById(rendezvous.getIdMedecin());
        Notification notification=new Notification();
        notification.setContenu("Le medecin "+medecin.getNom()+" est fixé un rendez-vous pour le " +
                rendezvous.getDateDebut());
        notification.setFromQui("MEDECIN");
        notification.setIdMedecin(rendezvous.getIdMedecin());
        notification.setIdPatient(rendezvous.getIdPatient());
        notification.setType("rendezVous");
        notification.setCreated(new Date());
        notification.setUrl("rendezvousp");
        notificationRepository.save(notification);

        Collection<Notification> notifications=notificationRepository.findByIdPatientAndFromQuiNotContainsOrderByCreatedDesc(rendezvous.getIdPatient(),"PATIENT");
        simpMessagingTemplate.convertAndSend("/topic/notifications",notifications);
        rendezvous.setValidation("Rendez-Vous non validé");
        return rendezvousRepository.save(rendezvous);
    }
    @DeleteMapping("/rendezVous/{id}")
    Rendezvous delete(@PathVariable Long id){
        Rendezvous rendezvous=rendezvousRepository.findById(id).get();
         rendezvousRepository.delete(rendezvous);
        Medecin medecin=medPatService.findMedecinById(rendezvous.getIdMedecin());
        Notification notification=new Notification();
        notification.setContenu("Le medecin "+medecin.getNom()+" est supprimé le rendez-vous de " +
                rendezvous.getDateDebut());
        notification.setFromQui("MEDECIN");
        notification.setIdMedecin(rendezvous.getIdMedecin());
        notification.setIdPatient(rendezvous.getIdPatient());
        notification.setType("rendezVous");
        notification.setCreated(new Date());
        notification.setUrl("rendezvousp");
        notificationRepository.save(notification);

        Collection<Notification> notifications=notificationRepository.findByIdPatientAndFromQuiNotContainsOrderByCreatedDesc(rendezvous.getIdPatient(),"PATIENT");
        simpMessagingTemplate.convertAndSend("/topic/notifications",notifications);
         return rendezvous;
    }
    @PutMapping("/rendezVous")
    Rendezvous update(@RequestBody Rendezvous rendezvous){
        Rendezvous rendezvous1=rendezvousRepository.findById(rendezvous.getId()).get();
         rendezvous1.setTitre(rendezvous.getTitre());
         rendezvous1.setIdPatient(rendezvous.getIdPatient());
         rendezvous1.setIdMedecin(rendezvous.getIdMedecin());
         rendezvous1.setDateDebut(rendezvous.getDateDebut());
         rendezvous1.setDateFin(rendezvous.getDateFin());
         rendezvous1.setValidation("Rendez-Vous non validé");

        Medecin medecin=medPatService.findMedecinById(rendezvous.getIdMedecin());
        Notification notification=new Notification();
        notification.setContenu("Le medecin "+medecin.getNom()+" est modifié le rendez-vous de " +
                rendezvous1.getDateDebut());
        notification.setFromQui("MEDECIN");
        notification.setIdMedecin(rendezvous.getIdMedecin());
        notification.setIdPatient(rendezvous.getIdPatient());
        notification.setType("rendezVous");
        notification.setCreated(new Date());
        notification.setUrl("rendezvousp");
        notificationRepository.save(notification);

        Collection<Notification> notifications=notificationRepository.findByIdPatientAndFromQuiNotContainsOrderByCreatedDesc(rendezvous.getIdPatient(),"PATIENT");
        simpMessagingTemplate.convertAndSend("/topic/notifications",notifications);
        return rendezvousRepository.save(rendezvous1);
    }

    @GetMapping("/rendezVousP/{idPat}")
    Collection<Rendezvous> getRendezP(@PathVariable Long idPat){
        Collection<Notification> notifications=notificationRepository.findByIdPatientAndFromQuiNotContainsOrderByCreatedDesc(idPat,"PATIENT");
        Collection<Rendezvous> rendezvous=rendezvousRepository.findByIdPatientOrderByDateDebutDesc(idPat);
        rendezvous.forEach(ren->{
            ren.setMedecin(medPatService.findMedecinById(ren.getIdMedecin()));
        });
        notifications.forEach(noti->{
            noti.setReaded(new Date());
            notificationRepository.save(noti);
        });

        simpMessagingTemplate.convertAndSend("/topic/notifications",notifications);
        return rendezvous;
    }
    @GetMapping("/rendezVousFlutter/{idPat}")
    Collection<Rendezvous> getRendezPFlutter(@PathVariable Long idPat){
        Collection<Notification> notifications=notificationRepository.findByIdPatientAndFromQuiNotContainsOrderByCreatedDesc(idPat,"PATIENT");
        Collection<Rendezvous> rendezvous=rendezvousRepository.findByIdPatientOrderByDateDebutDesc(idPat);

        notifications.forEach(noti->{
            noti.setReaded(new Date());
            notificationRepository.save(noti);
        });

        simpMessagingTemplate.convertAndSend("/topic/notifications",notifications);
        return rendezvous;
    }
    @GetMapping("/validerRendez/{idRendez}")
    Rendezvous validerRendez(@PathVariable Long idRendez){
        Rendezvous rendezvous=rendezvousRepository.findById(idRendez).get();
        rendezvous.setValidation("Rendez-Vous validé");
        Patient patient=medPatService.findPatient2ById(rendezvous.getIdPatient());
        Notification notification=new Notification();
        notification.setContenu("Le patient "+patient.getNom()+" est validé le rendez-vous de " +
                rendezvous.getDateDebut());
        notification.setFromQui("PATIENT");
        notification.setIdMedecin(rendezvous.getIdMedecin());
        notification.setIdPatient(rendezvous.getIdPatient());
        notification.setType("rendezVousValidation");
        notification.setCreated(new Date());
        notification.setUrl("rendezvous");
        notificationRepository.save(notification);

        Collection<Notification> notifications=notificationRepository.findByIdPatientAndFromQuiNotContainsOrderByCreatedDesc(rendezvous.getIdMedecin(),"MEDECIN");
        simpMessagingTemplate.convertAndSend("/topic/notifications",notifications);
        return rendezvousRepository.save(rendezvous);
    }

    @GetMapping("/notifications/{idUser}/{role}")
    Collection<Notification> getNotification(@PathVariable("idUser") Long idUser,@PathVariable("role") String role){
      if(role.equals("PATIENT")) return notificationRepository.findByIdPatientAndFromQuiNotContainsOrderByCreatedDesc(idUser,role);
      else return notificationRepository.findByIdMedecinAndFromQuiNotContainsOrderByCreatedDesc(idUser,role);
    }
}
