package org.sid.deviceservice;


import org.sid.deviceservice.dao.*;
import org.sid.deviceservice.entities.*;
import org.sid.deviceservice.service.DeviceService;
import org.sid.deviceservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Date;


@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class DeviceServiceApplication implements CommandLineRunner {
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    CapteurRepository capteurRepository;
    @Autowired
    FeildRepository feildRepository;
    @Autowired
    DonneeRepository donneeRepository;
    @Autowired
    PatientService patientService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    ChanelRepository chanelRepository;
    public static void main(String[] args) {
        SpringApplication.run(DeviceServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

      /*  Device d1=deviceRepository.save(new Device(null,123L,"device1","sheild Mysignal",1L,null));

          Chanel chanel1=chanelRepository.save(new Chanel(null,"chanel1",c1,1L,null,null));
        Chanel chanel2=chanelRepository.save(new Chanel(null,"chanel2",c2,1L,null,null));
        Chanel chanel3=chanelRepository.save(new Chanel(null,"chanel3",c3,1L,null,null));
        Chanel chanel4=chanelRepository.save(new Chanel(null,"chanel4",c4,1L,null,null));

        Feild f1=feildRepository.save(new Feild(null,"temp",40.0,0.0,chanel1,null));
        Feild f2=feildRepository.save(new Feild(null,"ECG wave",40.0,0.0,chanel2,null));
        Feild f3=feildRepository.save(new Feild(null,"bpm",40.0,0.0,chanel2,null));*/




        /*Capteur c1=capteurRepository.save(new Capteur(null,"temprature","temp_ok.png",null));
        Capteur c2=capteurRepository.save(new Capteur(null,"ECG","ecg_ok.png",null));
        Capteur c3=capteurRepository.save(new Capteur(null,"Spo2","spo2_ok.png",null));
        Capteur c4=capteurRepository.save(new Capteur(null,"Airflow","airflow_ok.png",null));
        Capteur c5=capteurRepository.save(new Capteur(null,"Blood Pressure","blood_ok.png",null));*/


    }


}
