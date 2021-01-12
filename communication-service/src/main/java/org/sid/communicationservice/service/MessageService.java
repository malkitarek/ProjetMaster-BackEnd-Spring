package org.sid.communicationservice.service;

import org.sid.communicationservice.entities.Messagemp;
import org.sid.communicationservice.entities.Patient;

import java.util.Collection;

public interface MessageService {
    public Collection<Messagemp> getlistMembres(Long idUser,String roleUser);
}
