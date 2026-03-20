package com.carebridge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.carebridge.entity.EHR;

import java.util.List;

public interface EHRRepository extends JpaRepository<EHR, Long> {

    List<EHR> findByPatientId(Long patientId);

}