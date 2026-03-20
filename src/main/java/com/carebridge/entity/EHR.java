package com.carebridge.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
public class EHR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diagnosis;
    private String doctorNotes;
    private LocalDate recordDate;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}