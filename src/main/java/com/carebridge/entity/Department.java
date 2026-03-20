
package com.carebridge.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;  

    @OneToMany(mappedBy = "department")
    private List<Doctor> doctors;
}