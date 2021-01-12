package org.sid.suivipatientservice.dao;

import org.sid.suivipatientservice.entities.Traitement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraitementRepositoy extends JpaRepository<Traitement,Long> {
}
