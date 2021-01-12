package org.sid.suivipatientservice.dao;

import org.sid.suivipatientservice.entities.Medecin;
import org.sid.suivipatientservice.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedecinRepository extends JpaRepository<Medecin,Long> {
    public Medecin findByGrade_Id(Long serId);
    public Medecin findByNumeroIdentite(Long numeroIdentite);
    public Medecin findByAppUserId(Long apLong);
}
