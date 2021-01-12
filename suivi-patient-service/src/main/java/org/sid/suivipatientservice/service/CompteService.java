package org.sid.suivipatientservice.service;

import org.sid.suivipatientservice.entities.AppUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "auth-service")
public interface CompteService {
    @GetMapping("/comptes/{id}")
    public AppUser findCompteById(@PathVariable Long id);

    @GetMapping("/compte/{email}")
    public AppUser findCompteByEmail(@PathVariable String email);

    @PostMapping("/comptes")
    public AppUser add(@RequestBody AppUser appUser);

    @PutMapping("/comptes/{idUser}")
    public AppUser updaat(@RequestBody AppUser appUser,@PathVariable Long idUser);

    @DeleteMapping("/comptes/{id}")
    public  void deleteCompte(@PathVariable Long id);

    @GetMapping("/verifePassword/{password}/{email}")
    boolean verife(@PathVariable("password") String password,@PathVariable("email") String email);
}
