package com.carebridge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carebridge.entity.EHR;
import com.carebridge.repository.EHRRepository;

@Service
public class EHRService {

    @Autowired
    private EHRRepository ehrRepository;

    public EHR saveRecord(EHR ehr) {
        return ehrRepository.save(ehr);
    }

    public List<EHR> getAllRecords() {
        return ehrRepository.findAll();
    }

    public List<EHR> getPatientRecords(Long patientId) {
        return ehrRepository.findByPatientId(patientId);
    }
}