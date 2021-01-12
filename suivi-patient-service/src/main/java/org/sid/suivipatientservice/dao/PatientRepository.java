package org.sid.suivipatientservice.dao;

import org.sid.suivipatientservice.entities.Medecin;
import org.sid.suivipatientservice.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient,Long> {
    public Patient findByNumeroIdentite(Long numeroIdentite);
    public Patient findByAppUserId(Long apLong);
    public Patient findByNumeroAssurance(Long numeroAssurance);

}
