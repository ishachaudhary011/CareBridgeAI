package com.carebridge.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carebridge.entity.EHR;
import com.carebridge.repository.EHRRepository;

@Service
public class EHRService {

    @Autowired
    private NLPService nlpService;

    @Autowired
    private EHRRepository ehrRepository;

    // 🔹 SAVE RECORD WITH NLP PROCESSING
    public EHR saveRecord(EHR ehr) {

        // STEP 1: Get diagnosis text from input
        String text = ehr.getDiagnosis();

        // STEP 2: Call NLP Service
        Map<String, Object> nlpResult = nlpService.extractData(text);

        // STEP 3: Extract values from NLP result
        String diagnosis = (String) nlpResult.get("diagnosis");
        String icdCode = (String) nlpResult.get("icdCode");

        // STEP 4: Set processed values in EHR
        ehr.setDiagnosis(diagnosis);   // cleaned diagnosis
        ehr.setIcdCode(icdCode);       // ICD code

        // STEP 5: (Optional) Store symptoms if field exists
        List<String> symptoms = (List<String>) nlpResult.get("symptoms");
        if (symptoms != null) {
            ehr.setSymptoms(symptoms.toString());
        }

        // STEP 6: Save to database
        return ehrRepository.save(ehr);
    }

    // 🔹 GET ALL RECORDS
    public List<EHR> getAllRecords() {
        return ehrRepository.findAll();
    }

    // 🔹 GET RECORDS BY PATIENT ID
    public List<EHR> getPatientRecords(Long patientId) {
        return ehrRepository.findByPatientId(patientId);
    }
}