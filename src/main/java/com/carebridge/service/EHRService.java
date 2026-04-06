package com.carebridge.service;

import com.carebridge.entity.EHR;
import com.carebridge.repository.EHRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EHRService {

    @Autowired
    private NLPService nlpService;

    @Autowired
    private EHRRepository ehrRepository;

    // ✅ Updated saveRecord to include NLP processing
    public EHR saveRecord(EHR ehr) {
        // Call NLP to extract diagnosis, ICD code, and symptoms
        Map<String, Object> nlpResult = (Map<String, Object>) nlpService.extractMedicalData(ehr.getDiagnosis());

        ehr.setDiagnosis((String) nlpResult.get("diagnosis"));
        ehr.setIcdCode((String) nlpResult.get("icdCode"));
        ehr.setSymptoms((String) nlpResult.get("symptoms"));

        return ehrRepository.save(ehr);
    }

    public List<EHR> getAllRecords() {
        return ehrRepository.findAll();
    }

    public List<EHR> getPatientRecords(Long patientId) {
        return ehrRepository.findByPatientId(patientId);
    }
}