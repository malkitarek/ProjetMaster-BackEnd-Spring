package org.sid.communicationservice.service;

import org.sid.communicationservice.dao.MessagempRepository;
import org.sid.communicationservice.entities.Messagemp;
import org.sid.communicationservice.entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessagempRepository messagempRepository;
    @Autowired
    MedPatService medPatService;

    @Override
    public Collection<Messagemp> getlistMembres(Long idUser, String roleUser) {
        if (roleUser.equals("MEDECIN")) {
            Collection<Messagemp> allmsgs = messagempRepository.findByIdMedecinOrderByCreatedDesc(idUser);
            Set<Long> set = new HashSet<>(allmsgs.size());
            allmsgs.removeIf(m -> !set.add(m.getIdPatient()));
            allmsgs.forEach(m -> {
                m.setPatient(medPatService.findPatient2ById(m.getIdPatient()));
            });
            return allmsgs;
        } else {
            Collection<Messagemp> allmsgs = messagempRepository.findByIdPatientOrderByCreatedDesc(idUser);
            Set<Long> set = new HashSet<>(allmsgs.size());
            allmsgs.removeIf(m -> !set.add(m.getIdMedecin()));
            allmsgs.forEach(m -> {
                m.setMedecin(medPatService.findMedecinById(m.getIdMedecin()));
            });
            return allmsgs;
        }

    }
}
