package org.sid.deviceservice.dao;

import org.sid.deviceservice.entities.Donnee;
import org.sid.deviceservice.entities.Feild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource
public interface DonneeRepository extends JpaRepository<Donnee,Long> {
    Collection<Donnee> findByFeild(Feild feild);
    Collection<Donnee> findByFeildOrderByTimeDesc(Feild feild);
}
