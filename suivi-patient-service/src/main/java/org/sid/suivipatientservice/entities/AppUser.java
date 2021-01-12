package org.sid.suivipatientservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data @NoArgsConstructor @AllArgsConstructor
public class AppUser {
    private Long id;
    private String email;
    private String phone;
    private String role;
}
