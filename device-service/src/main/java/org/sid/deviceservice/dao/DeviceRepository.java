package org.sid.deviceservice.dao;

import org.sid.deviceservice.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface DeviceRepository extends JpaRepository<Device,Long> {
    public Device findByPatientId(Long idPat);
}
