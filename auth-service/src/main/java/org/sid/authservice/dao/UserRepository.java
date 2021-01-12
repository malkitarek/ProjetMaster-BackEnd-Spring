package org.sid.authservice.dao;

import org.sid.authservice.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser,Long> {
    public AppUser findByEmail(String email);
}
