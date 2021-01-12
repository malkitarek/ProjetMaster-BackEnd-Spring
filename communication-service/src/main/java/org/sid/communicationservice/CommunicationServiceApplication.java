package org.sid.communicationservice;

import org.sid.communicationservice.dao.MessagempRepository;
import org.sid.communicationservice.entities.Messagemp;
import org.sid.communicationservice.entities.Patient;
import org.sid.communicationservice.service.MedPatService;
import org.sid.communicationservice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Collection;
import java.util.Date;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class CommunicationServiceApplication implements CommandLineRunner {
    @Autowired
    MessagempRepository messagempRepository;
    @Autowired
    MedPatService medPatService;
    @Autowired
    MessageService messageService;
    public static void main(String[] args) {
        SpringApplication.run(CommunicationServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
       /* Messagemp messageMP=new Messagemp(null,"salam",1L,1L,"MEDECIN",new Date(System.currentTimeMillis() + 3600 * 1000),
                new Date(System.currentTimeMillis() + 3600 * 1000),null,null);

        messagempRepository.save(messageMP);

        Messagemp messageMP2=new Messagemp(null,"salam",2L,1L,"MEDECIN",new Date(System.currentTimeMillis() + 3700 * 1000),
                new Date(System.currentTimeMillis() + 3700 * 1000),null,null);

        messagempRepository.save(messageMP2);
        Messagemp messageMP3=new Messagemp(null,"cc",3L,1L,"MEDECIN",new Date(System.currentTimeMillis() + 3700 * 1000),
                new Date(System.currentTimeMillis() + 3700 * 1000),null,null);

        messagempRepository.save(messageMP3);
        Messagemp messageMP4=new Messagemp(null,"mliha",4L,1L,"MEDECIN",new Date(System.currentTimeMillis() + 3700 * 1000),
                new Date(System.currentTimeMillis() + 3700 * 1000),null,null);

        messagempRepository.save(messageMP4);
        Messagemp messageMP5=new Messagemp(null,"cv",5L,1L,"MEDECIN",new Date(System.currentTimeMillis() + 3700 * 1000),
                new Date(System.currentTimeMillis() + 3700 * 1000),null,null);

        messagempRepository.save(messageMP5);*/
        /*Collection<Messagemp> membres=messageService.getlistMembres(1L);
        System.out.println("******************************");
        membres.forEach(m->{
            System.out.println(m.getPatient().getNom());
        });*/

    }
}
