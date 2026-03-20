package com.carebridge.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String specialization;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}