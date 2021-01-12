package org.sid.suivipatientservice.dao;

import org.sid.suivipatientservice.entities.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ConsultationRepository extends JpaRepository<Consultation,Long> {
    public Collection<Consultation> findByPatient_IdAndMedecin_Id(Long idPat,Long idMed);
}
