package org.sid.authservice.service;

import org.sid.authservice.entities.AppUser;

public interface AcountService {
    public AppUser saveUser(AppUser user);
    //public AppRole saveRole(AppRole role);
    //public void addRoleToUSer(String email,String roleName);
    public AppUser findUserByEmail(String email);
    public AppUser findUserById(Long id);
    public  void deleteCompte(Long id);
    public void SendEamil(String email,String contenu);
}
