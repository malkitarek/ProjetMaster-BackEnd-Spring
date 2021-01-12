package org.sid.communicationservice.dao;

import org.sid.communicationservice.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    Collection<Notification> findByIdPatientAndFromQuiNotContainsOrderByCreatedDesc(Long idPat,String role);
    Collection<Notification> findByIdMedecinAndFromQuiNotContainsOrderByCreatedDesc(Long idMed,String role);
}
