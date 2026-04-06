package com.carebridge.repository;

import com.carebridge.entity.EHR;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EHRRepository extends JpaRepository<EHR, Long> {

    List<EHR> findByPatientId(Long patientId);

}