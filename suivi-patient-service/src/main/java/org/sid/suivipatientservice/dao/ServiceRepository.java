package org.sid.suivipatientservice.dao;

import org.sid.suivipatientservice.entities.Medecin;
import org.sid.suivipatientservice.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service,Long> {
    public Service findByNom(String nom);
    public Service findByChefservice_Id(Long medecinId);
}
