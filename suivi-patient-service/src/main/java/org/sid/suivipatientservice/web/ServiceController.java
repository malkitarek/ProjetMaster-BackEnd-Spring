package org.sid.suivipatientservice.web;

import org.sid.suivipatientservice.dao.MedecinRepository;
import org.sid.suivipatientservice.dao.PatientRepository;
import org.sid.suivipatientservice.dao.ServiceRepository;
import org.sid.suivipatientservice.entities.Medecin;
import org.sid.suivipatientservice.entities.Patient;
import org.sid.suivipatientservice.entities.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class ServiceController {
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    MedecinRepository medecinRepository;
    @Autowired
    PatientRepository patientRepository;

    @GetMapping("/services")
    Collection<Service> listService() {
        return serviceRepository.findAll();
    }

    @GetMapping("/servicesPage")
    public Page<Service> listServicePage(@RequestParam(defaultValue = "0") int page) {
        return serviceRepository.findAll(PageRequest.of(page, 4));
    }

    @GetMapping("/services/{id}")
    public ResponseEntity<Service> getEmployeeById(@PathVariable(value = "id") Long serviceId)
            throws ResourceNotFoundException {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + serviceId));
        return ResponseEntity.ok().body(service);
    }

    @PostMapping("/services")
    public Service saveMed(@RequestBody ServiceForm serviceForm) {

        Service s = serviceRepository.findByNom(serviceForm.getNom());
        if (s != null) throw new RuntimeException("Service is already exist");
        Service service = new Service();
        service.setNom(serviceForm.getNom());
        service.setNomCHU(serviceForm.getNomCHU());
        if (serviceForm.getChefservice() != null)
            service.setChefservice(medecinRepository.findById(serviceForm.getChefservice()).get());

        return serviceRepository.save(service);
    }

    @DeleteMapping("/services/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long serviceId)
            throws ResourceNotFoundException {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + serviceId));

        serviceRepository.delete(service);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @PutMapping("/services/{id}")
    public ResponseEntity<Service> updateService(@RequestBody ServiceForm serviceDetails, @PathVariable(value = "id") Long serviceId) throws ResourceNotFoundException {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found for this id :: " + serviceId));


        if (!service.getNom().toLowerCase().equals(serviceDetails.getNom().toLowerCase())) {
            Service s = serviceRepository.findByNom(serviceDetails.getNom());
            if (s != null) {
                throw new RuntimeException("Service is already exist");
            }
        }
        service.setNom(serviceDetails.getNom());
        service.setNomCHU(serviceDetails.getNomCHU());

        if(serviceDetails.getChefservice()!= null)service.setChefservice(medecinRepository.findById(serviceDetails.getChefservice()).get());
        final Service updatedService = serviceRepository.save(service);
        return ResponseEntity.ok(updatedService);
    }

    @GetMapping("/chefService/{serId}")
    public Medecin getChefSer(@PathVariable Long serId) {
        return medecinRepository.findByGrade_Id(serId);
    }

    @GetMapping("/patsParSer")
    public List<PatsService> getPatsSer() {
        Collection<Patient> patients = patientRepository.findAll();
        Collection<Service> services = serviceRepository.findAll();
        List<PatsService> patsServices =new ArrayList<>();

        services.forEach(s -> {
            PatsService patsService = new PatsService(s.getNom(), 0);
            patients.forEach(p -> {
                Optional<Medecin> medecin = p.getMedecins().stream().filter(m -> m.getService().getNom().equals(s.getNom())).findFirst();
                if(medecin.isPresent()){
                    patsService.setNbrPats(patsService.getNbrPats()+1);
                }
            });
            if(patsService!=null){
                patsServices.add(patsService);
                System.out.println("*****************************"+patsService.getNbrPats()+"************************");
            }
        });
        return patsServices;
    }



}
