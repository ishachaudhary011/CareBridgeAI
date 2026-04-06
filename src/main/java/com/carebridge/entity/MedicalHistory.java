package com.carebridge.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String conditionName;
    private String description;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}