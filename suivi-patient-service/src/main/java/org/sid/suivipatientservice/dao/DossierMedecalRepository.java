package org.sid.suivipatientservice.dao;

import org.sid.suivipatientservice.entities.DossierMedecal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DossierMedecalRepository extends JpaRepository<DossierMedecal,Long> {
    //public DossierMedecal findBy
}
