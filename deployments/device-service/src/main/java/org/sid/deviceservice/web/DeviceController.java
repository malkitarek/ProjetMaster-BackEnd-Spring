package org.sid.deviceservice.web;

import org.sid.deviceservice.dao.CapteurRepository;
import org.sid.deviceservice.dao.ChanelRepository;
import org.sid.deviceservice.dao.DeviceRepository;
import org.sid.deviceservice.dao.FeildRepository;
import org.sid.deviceservice.entities.Capteur;
import org.sid.deviceservice.entities.Chanel;
import org.sid.deviceservice.entities.Device;
import org.sid.deviceservice.entities.Feild;
import org.sid.deviceservice.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
public class DeviceController {
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    PatientService patientService;
    @Autowired
    CapteurRepository capteurRepository;
    @Autowired
    ChanelRepository chanelRepository;
    @Autowired
    FeildRepository feildRepository;

    @GetMapping("/devices")
    Collection<Device> listDevices(){
        Collection<Device> devices=deviceRepository.findAll();
        devices.forEach(device -> {
            device.setPatient(patientService.findPatientById(device.getPatientId()));
        });
       return devices;
    }

    @GetMapping("/devices/{id}")
    Device getDevice(@PathVariable Long id){
        Device device=deviceRepository.findById(id).get();
        device.setPatient(patientService.findPatientById(device.getPatientId()));
        return device;
    }

    @PostMapping("/devices")
    Device saveDevice(@RequestBody Device deviceRequest ){
        Device d=deviceRepository.findByPatientId(deviceRequest.getPatientId());
        if(d!=null) throw new RuntimeException("patient already has a device");
        Device device=deviceRepository.save(deviceRequest);
        Collection<Capteur> capteurs=capteurRepository.findAll();
        List<Capteur> capteurs1=new ArrayList(capteurs);
        Chanel chanel1=chanelRepository.save(new Chanel(null,"chanel1",capteurs1.get(0),device.getPatientId(),null,null));
        Chanel chanel2=chanelRepository.save(new Chanel(null,"chanel2",capteurs1.get(1),device.getPatientId(),null,null));
        Chanel chanel3=chanelRepository.save(new Chanel(null,"chanel3",capteurs1.get(2),device.getPatientId(),null,null));
        Chanel chanel4=chanelRepository.save(new Chanel(null,"chanel4",capteurs1.get(3),device.getPatientId(),null,null));
        Chanel chanel5=chanelRepository.save(new Chanel(null,"chanel5",capteurs1.get(4),device.getPatientId(),null,null));

        Feild f1=feildRepository.save(new Feild(null,"temp",40.0,0.0,chanel1,null));
        Feild f2=feildRepository.save(new Feild(null,"ECG wave",40.0,0.0,chanel2,null));
        Feild f3=feildRepository.save(new Feild(null,"bpm",40.0,0.0,chanel2,null));

        Feild f4=feildRepository.save(new Feild(null,"diastolic",40.0,0.0,chanel5,null));
        Feild f5=feildRepository.save(new Feild(null,"systolic",40.0,0.0,chanel5,null));
        Feild f6=feildRepository.save(new Feild(null,"pulse",40.0,0.0,chanel5,null));

        Feild f7=feildRepository.save(new Feild(null,"spo2",40.0,0.0,chanel3,null));
        Feild f8=feildRepository.save(new Feild(null,"bpm_spo2",40.0,0.0,chanel3,null));
        return device;

    }

    @PutMapping("/devices")
    Device updateDevice(@RequestBody Device deviceRequest ){
        Device device=deviceRepository.findById(deviceRequest.getId()).get();
        device.setNom(deviceRequest.getNom());
        device.setDescription(deviceRequest.getDescription());
        device.setPatientId(deviceRequest.getPatientId());
       // device.setCapteurs(deviceRequest.getCapteurs());
        return deviceRepository.save(device);

    }

    @DeleteMapping("/devices/{id}/{idPat}")
    void deleteDevice(@PathVariable("id") Long id,@PathVariable("idPat") Long idPat){
        Device device=deviceRepository.findById(id).get();
        Collection<Chanel> chanels=chanelRepository.findByPatientId(idPat);
        Collection<Feild> feilds=feildRepository.findByChanel_PatientId(idPat);
        feilds.forEach(f->{
            feildRepository.delete(f);
        });
        chanels.forEach(c->{
            chanelRepository.delete(c);
        });
        deviceRepository.delete(device);
    }

    @GetMapping("/chanels/{idPat}")
    public Collection<Chanel> getChanels(@PathVariable Long idPat){

        return chanelRepository.findByPatientId(idPat);
    }

   @GetMapping("/device/{patientId}/{id}")
    public boolean validatePatient(@PathVariable("patientId") Long patientId,@PathVariable("id") Long id){

       if (id != -1) {
           Device device = deviceRepository.findById(id).get();
           //AppUser user = compteService.findCompteById(patient.getAppUserId());

           if (!device.getPatientId().equals(patientId)) {
               Device u = deviceRepository.findByPatientId(patientId);
               if (u != null) {
                   return true;
               }
           }

           return false;
       } else {
           Device u = deviceRepository.findByPatientId(patientId);
           if (u != null) {
               return true;
           }
       }

       return false;
   }

    /* @GetMapping("/capteurs")
    public Collection<Capteur> getCapts(){
        return capteurRepository.findAll();
    }
    @GetMapping("/capteurs/{idDev}")
    public Collection<Capteur> getCaptsSelet(@PathVariable Long idDev){
        Device device=deviceRepository.findById(idDev).get();
        Collection<Capteur> capteurs=capteurRepository.findAll();
        device.getCapteurs().forEach(c->{
            capteurs.remove(c);
        });
        return capteurs;
    }*/
}
