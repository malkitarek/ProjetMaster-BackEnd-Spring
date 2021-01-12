package org.sid.suivipatientservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.sid.suivipatientservice.dao.MedecinRepository;
import org.sid.suivipatientservice.dao.PatientRepository;
import org.sid.suivipatientservice.dao.ServiceRepository;
import org.sid.suivipatientservice.entities.AppUser;
import org.sid.suivipatientservice.entities.Medecin;
import org.sid.suivipatientservice.entities.Patient;
import org.sid.suivipatientservice.entities.Service;
import org.sid.suivipatientservice.service.CompteService;
import org.sid.suivipatientservice.service.SuiviService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class SuiviPatientServiceApplication implements CommandLineRunner {

    @Autowired
    CompteService compteService;
    @Autowired
    MedecinRepository medecinRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    SuiviService suiviService;

    public static void main(String[] args) {
        SpringApplication.run(SuiviPatientServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    /* AppUser admin=new AppUser(null,"tarek",null,"ADMIN");
        compteService.add(admin);
        Service s=new Service(null,"cardio",null,null,null);
        serviceRepository.save(s);
        AppUser user=new AppUser(null,"malkitarek96@gmail.com","0557882216","MEDECIN");
        compteService.add(user);
        medecinRepository.save(new Medecin(null,1L,2L,1996L,"malki","tarek","12 cité adda boudjlal",null,compteService.findCompteByEmail("malkitarek96@gmail.com").getId(),null,s,null,null,null));

        AppUser user2=new AppUser(null,"malki.m.tarek@gmail.com","0657557649","PATIENT");
        compteService.add(user2);
        patientRepository.save(new Patient(null,1998L,"malkiPatient","tarek","HOMME",1L,
                "12 cité adda boudjlal",null,
                compteService.findCompteByEmail("malki.m.tarek@gmail.com").getId(),null,null,null,null));

        AppUser p2=new AppUser(null,"malkitarekp2@gmail.com","0657557649","PATIENT");
        compteService.add(p2);
        patientRepository.save(new Patient(null,1999L,"malkiPatientp2","tarek","HOMME",2L,
                "12 cité adda boudjlal",null,
                compteService.findCompteByEmail("malkitarekp2@gmail.com").getId(),null,null,null,null));

        AppUser p3=new AppUser(null,"malkitarekp3@gmail.com","0657557649","PATIENT");
        compteService.add(p3);
        patientRepository.save(new Patient(null,2000L,"malkiPatientp3","tarek","HOMME",3L,
                "12 cité adda boudjlal",null,
                compteService.findCompteByEmail("malkitarekp3@gmail.com").getId(),null,null,null,null));

        AppUser p4=new AppUser(null,"malkitarekp4@gmail.com","0657557649","PATIENT");
        compteService.add(p4);
        patientRepository.save(new Patient(null,2001L,"malkiPatientp4","tarek","HOMME",4L,
                "12 cité adda boudjlal",null,
                compteService.findCompteByEmail("malkitarekp4@gmail.com").getId(),null,null,null,null));

        AppUser p5=new AppUser(null,"malkitarekp5@gmail.com","0657557649","PATIENT");
        compteService.add(p5);
        patientRepository.save(new Patient(null,2002L,"malkiPatientp5","tarek","HOMME",5L,
                "12 cité adda boudjlal",null,
                compteService.findCompteByEmail("malkitarekp5@gmail.com").getId(),null,null,null,null));



        AppUser user3=new AppUser(null,"malkitarekM196@gmail.com","0557882216","MEDECIN");
        compteService.add(user3);
        medecinRepository.save(new Medecin(null,1L,2L,19966L,"malkiM1","tarek", "12 cité adda boudjlal",
                null,compteService.findCompteByEmail("malkitarekM196@gmail.com").getId(),
                null,null,null,null,null));

        AppUser user4=new AppUser(null,"malkitarekM296@gmail.com","0557882216","MEDECIN");
        compteService.add(user4);
        medecinRepository.save(new Medecin(null,1L,2L,199667L,"malkiM2","tarek", "12 cité adda boudjlal",
                null,compteService.findCompteByEmail("malkitarekM296@gmail.com").getId(),
                null,null,null,null,null));

        AppUser user5=new AppUser(null,"malkitarekM396@gmail.com","0557882216","MEDECIN");
        compteService.add(user5);
        medecinRepository.save(new Medecin(null,1L,2L,199668L,"malkiM3","tarek", "12 cité adda boudjlal",
                null,compteService.findCompteByEmail("malkitarekM396@gmail.com").getId(),
                null,null,null,null,null));

        AppUser user6=new AppUser(null,"malkitarekM496@gmail.com","0557882216","MEDECIN");
        compteService.add(user6);
        medecinRepository.save(new Medecin(null,1L,2L,199669L,"malkiM4","tarek", "12 cité adda boudjlal",
                null,compteService.findCompteByEmail("malkitarekM496@gmail.com").getId(),
                null,null,null,null,null));

        AppUser user7=new AppUser(null,"malkitarekM596@gmail.com","0557882216","MEDECIN");
        compteService.add(user7);
        medecinRepository.save(new Medecin(null,1L,2L,199670L,"malkiM5","tarek", "12 cité adda boudjlal",
                null,compteService.findCompteByEmail("malkitarekM596@gmail.com").getId(),
                null,null,null,null,null));
        suiviService.addPatientToMaedecin(1996L,1998L);
        suiviService.addPatientToMaedecin(1996L,1999L);
        suiviService.addPatientToMaedecin(1996L,2000L);
        suiviService.addPatientToMaedecin(1996L,2001L);
        suiviService.addPatientToMaedecin(1996L,2002L);

        suiviService.addPatientToMaedecin(19966L,1999L);
        suiviService.addPatientToMaedecin(19966L,2000L);
        suiviService.addPatientToMaedecin(19966L,2001L);

        suiviService.addPatientToMaedecin(199667L,1998L);
        suiviService.addPatientToMaedecin(199667L,1999L);
        suiviService.addPatientToMaedecin(199667L,2000L);
        suiviService.addPatientToMaedecin(199667L,2001L);

        suiviService.addPatientToMaedecin(199668L,1998L);
        suiviService.addPatientToMaedecin(199668L,1999L);
        suiviService.addPatientToMaedecin(199668L,2000L);
        suiviService.addPatientToMaedecin(199668L,2001L);
        suiviService.addPatientToMaedecin(199668L,2002L);

        suiviService.addPatientToMaedecin(199669L,1998L);
        suiviService.addPatientToMaedecin(199669L,2001L);
        suiviService.addPatientToMaedecin(199669L,2002L);

        suiviService.addPatientToMaedecin(199670L,2000L);
        suiviService.addPatientToMaedecin(199670L,2001L);
*/
    }
}
