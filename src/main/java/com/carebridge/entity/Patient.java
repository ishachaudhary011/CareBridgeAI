package com.carebridge.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String gender;
    private String phone;
    private Integer age;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

}