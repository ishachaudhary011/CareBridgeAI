package com.carebridge.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String medicineName;
    private String dosage;
    private String duration;

    @ManyToOne
    @JoinColumn(name = "ehr_id")
    private EHR ehr;
}