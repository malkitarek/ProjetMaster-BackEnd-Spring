package org.sid.communicationservice.web;

import org.sid.communicationservice.dao.MessagempRepository;
import org.sid.communicationservice.entities.Messagemp;
import org.sid.communicationservice.service.MedPatService;
import org.sid.communicationservice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

@RestController
public class MessagempController {
    @Autowired
    MessagempRepository messagempRepository;
    @Autowired
    MedPatService medPatService;
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    MessageService messageService;


    @GetMapping("/messageLast/{idUser}/{roleUser}")
    Long getPatLastMsg(@PathVariable("idUser") Long idUser,@PathVariable("roleUser") String roleUser){

      Messagemp messagemp= messagempRepository.findFirstByIdMedecinOrderByCreatedDesc(idUser);
      if(roleUser.equals("MEDECIN")){
          if(messagemp!=null)
          return  messagemp.getIdPatient();
          else return null;
      }
      else {
          if(messagemp!=null)
          return  messagemp.getIdMedecin();
          else return null;
      }

    }
    @GetMapping("/messages/{idUser}/{idMem}/{role}")
    Collection<Messagemp> getMessages(@PathVariable("idUser") Long idUser,@PathVariable("idMem") Long idMem,@PathVariable("role") String role){


        Collection<Messagemp> messages=null;
       if(role.equals("MEDECIN")) messages=messagempRepository.findByIdMedecinAndIdPatientOrderByCreatedAsc(idUser,idMem);
       else messages=messagempRepository.findByIdMedecinAndIdPatientOrderByCreatedAsc(idMem,idUser);
       messages.forEach(m->{
           if(m.getReaded()==null && !m.getFromQui().equals(role))
           {m.setReaded(new Date(System.currentTimeMillis() + 3600 * 1000));
           messagempRepository.save(m);
           }
       });
        Collection<Messagemp> allMessages=null;
        Messagemp messagemp=new Messagemp();
        if(role.equals("MEDECIN")){
            allMessages=messagempRepository.findByIdMedecinAndFromQuiNotContainsAndReadedNull(idUser,role);
            messagemp.setIdMedecin(idUser);
            messagemp.setFromQui("TAREK");
        }
        else{
            allMessages=messagempRepository.findByIdPatientAndFromQuiNotContainsAndReadedNull(idUser,role);
            messagemp.setIdPatient(idUser);
            messagemp.setFromQui("TAREK");
        }

       // Collection<Messagemp> membres=messageService.getlistMembres(idUser,role);
        //simpMessagingTemplate.convertAndSend("/topic/messages",messages);
        //simpMessagingTemplate.convertAndSend("/topic/msgsMembres",membres);


        if(allMessages.size()==0)allMessages.add(messagemp);
        simpMessagingTemplate.convertAndSend("/topic/allMessages",allMessages);

        if(role.equals("MEDECIN"))return  messagempRepository.findByIdMedecinAndIdPatientOrderByCreatedAsc(idUser,idMem);
        else return  messagempRepository.findByIdMedecinAndIdPatientOrderByCreatedAsc(idMem,idUser);


    }
    @GetMapping("/membresMsg/{idUser}/{roleUser}")
    Collection<Messagemp> getMembres(@PathVariable("idUser") Long idUser,@PathVariable("roleUser") String roleUser){
        Collection<Messagemp> mesgs=messageService.getlistMembres(idUser,roleUser);
        return mesgs;
    }

    @PostMapping("/messages")
    void sendMsg(@RequestBody Messagemp messagemp){
        Messagemp messagemp1=new Messagemp();
        messagemp1.setContenu(messagemp.getContenu());
        messagemp1.setIdMedecin(messagemp.getIdMedecin());
        messagemp1.setIdPatient(messagemp.getIdPatient());
        messagemp1.setFromQui(messagemp.getFromQui());
        messagemp1.setCreated(new Date(System.currentTimeMillis() + 3600 * 1000));
        messagemp1.setReaded(null);
        Messagemp m=messagempRepository.save(messagemp1);

        Collection<Messagemp> messages=messagempRepository.findByIdMedecinAndIdPatient(messagemp.getIdMedecin(),messagemp.getIdPatient());;
        Collection<Messagemp> allMessages=null;
        Collection<Messagemp> membres=null;
        if (messagemp.getFromQui().equals("MEDECIN")){
            allMessages=messagempRepository.findByIdPatientAndFromQuiNotContainsAndReadedNull(messagemp.getIdPatient(),"PATIENT");
            membres=messageService.getlistMembres(messagemp.getIdPatient(),"PATIENT");
        }
       else {
            allMessages=messagempRepository.findByIdMedecinAndFromQuiNotContainsAndReadedNull(messagemp.getIdMedecin(),"MEDECIN");
            membres=messageService.getlistMembres(messagemp.getIdMedecin(),"MEDECIN");
       }


        simpMessagingTemplate.convertAndSend("/topic/messages",messages);
        simpMessagingTemplate.convertAndSend("/topic/msgsMembres",membres);
        simpMessagingTemplate.convertAndSend("/topic/allMessages",allMessages);

    }
    @GetMapping("/messagesUnread/{idUser}/{idMem}/{role}")
    int getUnreadMessage(@PathVariable("idUser") Long idUser,@PathVariable("idMem") Long idMem,@PathVariable("role") String role){
        Collection<Messagemp> messagemps=null;
      if(role.equals("MEDECIN"))
        messagemps=messagempRepository.findByIdMedecinAndIdPatientAndFromQuiNotContainsAndReadedNull(idUser,idMem,role);
      else
          messagemps=messagempRepository.findByIdMedecinAndIdPatientAndFromQuiNotContainsAndReadedNull(idMem,idUser,role);
        return messagemps.size();
    }

    @GetMapping("/allmessagesUnread/{idUser}/{fromQui}")
    int getUnreadMessage(@PathVariable("idUser") Long idUser,@PathVariable("fromQui") String fromQui){
        Collection<Messagemp> messagemps=null;
        if (fromQui.equals("MEDECIN"))  messagemps=messagempRepository.findByIdMedecinAndFromQuiNotContainsAndReadedNull(idUser,fromQui);
        else  messagemps=messagempRepository.findByIdPatientAndFromQuiNotContainsAndReadedNull(idUser,fromQui);
        return messagemps.size();
    }

}
