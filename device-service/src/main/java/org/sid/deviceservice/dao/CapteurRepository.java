package org.sid.deviceservice.dao;

import org.sid.deviceservice.entities.Capteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CapteurRepository extends JpaRepository<Capteur,Long> {
}
