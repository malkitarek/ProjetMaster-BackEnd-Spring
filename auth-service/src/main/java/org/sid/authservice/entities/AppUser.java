package org.sid.authservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;
@Entity
@NoArgsConstructor @AllArgsConstructor
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String phone;

    private String password;

    @Getter @Setter
    private int active;

    @Getter @Setter
    private int codeConfirm;

    @Getter @Setter
    private String role;

    /*@ManyToMany(fetch = FetchType.EAGER)
    @Getter @Setter
    private Collection<AppRole> roles;*/

    @JsonIgnore
    public String getPassword() {
        return password;
    }
    @JsonSetter
    public void setPassword(String password) {
        this.password = password;
    }

}
