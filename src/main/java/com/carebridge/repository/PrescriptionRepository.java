package com.carebridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.carebridge.entity.Prescription;

import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findByEhrId(Long ehrId);

}