package com.carebridge.service;

import com.carebridge.DTO.MedicalResponse;
import com.carebridge.entity.EHR;
import com.carebridge.repository.EHRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EHRService {

    @Autowired
    private NLPService nlpService;

    @Autowired
    private EHRRepository ehrRepository;

    // ✅ SAVE RECORD WITH NLP
    public EHR saveRecord(EHR ehr) {

        // 🔥 Call NLP correctly
        MedicalResponse response = nlpService.extractMedicalData(ehr.getDiagnosis());

        // ✅ Map NLP → EHR safely

        if (response != null) {

            // diseases → diagnosis
            if (response.getDiseases() != null && !response.getDiseases().isEmpty()) {
                ehr.setDiagnosis(String.join(", ", response.getDiseases()));
            }

            // symptoms list → string
            if (response.getSymptoms() != null) {
                ehr.setSymptoms(String.join(", ", response.getSymptoms()));
            }

            // medications → you can store if field exists
            if (response.getMedications() != null) {
                ehr.setMedications(String.join(", ", response.getMedications()));
            }

            // Optional: ICD code logic later (AI or mapping)
            ehr.setIcdCode("N/A");
        }

        return ehrRepository.save(ehr);
    }

    public List<EHR> getAllRecords() {
        return ehrRepository.findAll();
    }

    public List<EHR> getPatientRecords(Long patientId) {
        return ehrRepository.findByPatientId(patientId);
    }
}