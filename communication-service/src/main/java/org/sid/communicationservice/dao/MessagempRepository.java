package org.sid.communicationservice.dao;

import org.sid.communicationservice.entities.Messagemp;
import org.sid.communicationservice.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface MessagempRepository extends JpaRepository<Messagemp,Long> {
    public Messagemp findFirstByIdMedecinOrderByCreatedDesc(Long idMed);
    public Collection<Messagemp> findByIdMedecinAndIdPatientOrderByCreatedAsc(Long idMed,Long idPat);
    public Collection<Messagemp> findByIdMedecinAndIdPatient(Long idMed,Long idPat);
    public Collection<Messagemp> findByIdMedecinAndIdPatientAndFromQuiNotContainsAndReadedNull(Long idMed,Long idPat,String fromQui);
    public Collection<Messagemp> findByIdMedecinAndFromQuiNotContainsAndReadedNull(Long idMed,String fromQui);
    public Collection<Messagemp> findByIdPatientAndFromQuiNotContainsAndReadedNull(Long idPat,String fromQui);
    public Collection<Messagemp> findByIdMedecinAndFromQuiAndReadedNull(Long idMed,String fromQui);
    public Collection<Messagemp> findByIdPatientAndFromQuiAndReadedNull(Long idPat,String fromQui);
    public Collection<Messagemp> findByIdMedecinOrderByCreatedDesc(Long idMed);
    public Collection<Messagemp> findByIdPatientOrderByCreatedDesc(Long idUser);

}
