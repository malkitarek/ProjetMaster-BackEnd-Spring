package org.sid.deviceservice.dao;

import org.sid.deviceservice.entities.Feild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource
public interface FeildRepository extends JpaRepository<Feild,Long> {
    public Feild findByNom(String nom);
    public Feild findByNomAndChanel_PatientId(String nom,Long idPat);
    public Feild findByChanel_PatientIdAndChanel_IdAndNom(Long idPat,Long idCahnel,String nomFeild);
    public Collection<Feild> findByChanel_PatientIdAndChanel_Id(Long idPat, Long idCahnel);
    public Collection<Feild> findByChanel_PatientId(Long idPat);
}
