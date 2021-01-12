package org.sid.deviceservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Donnee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double valeur;
    //@JsonFormat(pattern="YYYY-MM-DD HH:mm:ss")
    private Date time;
    @ManyToOne
    private Feild feild;

}
