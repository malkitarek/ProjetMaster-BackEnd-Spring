package org.sid.suivipatientservice.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceForm {
    String nom;
    String nomCHU;
    Long chefservice;
}
