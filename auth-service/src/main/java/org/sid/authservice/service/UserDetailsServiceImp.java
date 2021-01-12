package org.sid.authservice.service;


import org.sid.authservice.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private AcountService acountService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        AppUser user=acountService.findUserByEmail(email);

        if (user==null)throw new UsernameNotFoundException(email);

        Collection<GrantedAuthority> authorities=new ArrayList<>();

       /* user.getRoles().forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        });*/
       authorities.add(new SimpleGrantedAuthority(user.getRole()));
        if(user.getActive()!=1) throw new RuntimeException("compte inactivé");
        return new User(user.getEmail(),user.getPassword(),authorities);
    }
}
