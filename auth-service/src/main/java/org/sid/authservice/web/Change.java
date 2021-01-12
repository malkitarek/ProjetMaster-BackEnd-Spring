package org.sid.authservice.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Change {
    private String actuel;
    private String password;
    private String repassword;
}
