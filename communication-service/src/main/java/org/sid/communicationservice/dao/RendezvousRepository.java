package org.sid.communicationservice.dao;

import org.sid.communicationservice.entities.Rendezvous;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface RendezvousRepository extends JpaRepository<Rendezvous,Long> {
    public Collection<Rendezvous> findByIdMedecin(Long idMedecin);
    public  Collection<Rendezvous> findByIdMedecinOrderByDateDebutDesc(Long idMedecin);
    public Collection<Rendezvous> findByIdPatient(Long idPat);
    public Collection<Rendezvous> findByIdPatientOrderByDateDebutDesc(Long idPat);
}
