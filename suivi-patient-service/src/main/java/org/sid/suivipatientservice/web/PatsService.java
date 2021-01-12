package org.sid.suivipatientservice.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class PatsService {
    private String nomService;
    private int nbrPats;
}
