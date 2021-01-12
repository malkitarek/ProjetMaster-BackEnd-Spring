package org.sid.authservice.web;

import org.sid.authservice.dao.UserRepository;
import org.sid.authservice.entities.AppUser;
import org.sid.authservice.service.AcountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@CrossOrigin("*")
public class AcounRestController {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AcountService acountService;

    @PostMapping("/register/{etat}")
    public AppUser register(@RequestBody RegisterForm userRegister, @PathVariable int etat) {

        //System.out.println("**************"+bCryptPasswordEncoder.encode(userRegister.getPassword())+"********************");

        if (!userRegister.getPassword().equals(userRegister.getRepassword()))
            throw new RuntimeException("You must confirm mot de passe");
        AppUser auser = acountService.findUserByEmail(userRegister.getEmail());
        if (auser == null) throw new RuntimeException("Email not exist");
        if (auser != null && etat == 0 && auser.getActive() == 1) throw new RuntimeException("User is already exist");
        if (auser != null && etat == 1 && auser.getActive() == 1) auser.setActive(0);
        Random r = new Random();
        int random = r.nextInt(9999);
        try {
            acountService.SendEamil(auser.getEmail(), String.valueOf(random));
        } catch (RuntimeException e) {
            throw new RuntimeException("Invalid Adresse");
        }
        auser.setCodeConfirm(random);
        auser.setPassword(bCryptPasswordEncoder.encode(userRegister.getPassword()));
        return acountService.saveUser(auser);

    }

    @PostMapping("/confirme/{id}")
    public AppUser confirmation(@RequestBody ConfirmForm codeUser, @PathVariable Long id) {
        System.out.println(codeUser.getCode());
        AppUser user = acountService.findUserById(id);
        if (user.getCodeConfirm() == codeUser.getCode()) user.setActive(1);

        return acountService.saveUser(user);

    }

    @GetMapping("/comptes/{id}")
    public AppUser findCompteById(@PathVariable Long id) {
        return acountService.findUserById(id);
    }

    @GetMapping("/compte/{email}")
    public AppUser findCompteByEmail(@PathVariable String email) {
        return acountService.findUserByEmail(email);
    }

    @PostMapping("/comptes")
    public AppUser add(@RequestBody AppUser appUser) {
        return acountService.saveUser(appUser);
        //acountService.addRoleToUSer(appUser.getEmail(),role);
    }

    @PutMapping("/comptes/{idUser}")
    public AppUser updaat(@RequestBody AppUser appUser, @PathVariable Long idUser) {
        AppUser user = acountService.findUserById(idUser);
        user.setPhone(appUser.getPhone());
        user.setEmail(appUser.getEmail());
        return acountService.saveUser(user);
        //acountService.addRoleToUSer(appUser.getEmail(),role);
    }

    @DeleteMapping("/comptes/{id}")
    public void deleteCompte(@PathVariable Long id) {
        acountService.deleteCompte(id);
    }

    @PostMapping("/change/{email}")
    public AppUser changerMdp(@RequestBody Change changePass, @PathVariable String email) {
        AppUser admin = acountService.findUserByEmail(email);

        String Newpass = bCryptPasswordEncoder.encode(changePass.getPassword());


        if (!bCryptPasswordEncoder.matches(changePass.getActuel(), admin.getPassword()))
            throw new RuntimeException("Invalid password");

        if (!changePass.getPassword().equals(changePass.getRepassword()))
            throw new RuntimeException("You must confirm mot de passe");

        admin.setPassword(Newpass);

        return acountService.saveUser(admin);
    }

    @GetMapping("/verifePassword/{password}/{email}")
    boolean verife(@PathVariable("password") String password, @PathVariable("email") String email) {
        AppUser user = acountService.findUserByEmail(email);

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) return false;
        else return true;
    }

    @GetMapping("/user/{password}/{email}")
    public boolean validateIdntite(@PathVariable("password") String password, @PathVariable("email") String email) {

        AppUser user = acountService.findUserByEmail(email);
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) return true;
        else return false;
    }
}
