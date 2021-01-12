package org.sid.authservice.service;

import org.sid.authservice.dao.UserRepository;
import org.sid.authservice.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AcountServiceImpl implements AcountService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public AppUser saveUser(AppUser user) {
        return userRepository.save(user);
    }

    /*@Override
    public AppRole saveRole(AppRole role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUSer(String email, String roleName) {
        AppRole role=roleRepository.findByRoleName(roleName);
        AppUser user=userRepository.findByEmail(email);
        user.getRoles().add(role);
    }*/

    @Override
    public AppUser findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public AppUser findUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void deleteCompte(Long id) {
        userRepository.delete(findUserById(id));
    }

    @Override
    public void SendEamil(String email, String contenu) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("SpringSend Email Test");
        message.setText(contenu);
        javaMailSender.send(message);
    }
}
