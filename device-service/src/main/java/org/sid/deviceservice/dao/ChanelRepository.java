package org.sid.deviceservice.dao;

import org.sid.deviceservice.entities.Chanel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ChanelRepository extends JpaRepository<Chanel,Long> {
    public Collection<Chanel> findByPatientId(Long idPat);
}
