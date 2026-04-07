package com.carebridge.service;

import com.carebridge.DTO.MedicalResponse;
import com.carebridge.controller.TranscriptionController;
import com.carebridge.entity.EHR;
import com.carebridge.repository.EHRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EHRService {

    @Autowired
    private NLPService nlpService;

    @Autowired
    private TranscriptionController transcriptionController;   // 🔥 NEW

    @Autowired
    private ICDService icdService;         // 🔥 NEW

    @Autowired
    private EHRRepository ehrRepository;

    // 🔥 NEW COMPLETE PIPELINE (Audio → NLP → ICD → DB)
    public EHR processAudio(MultipartFile file) throws IOException {

        // ✅ 1. Speech → Text
        String transcription = String.valueOf(transcriptionController.transcribeAudio(file));
        System.out.println("Transcription: " + transcription);

        // ✅ 2. NLP Extraction
        MedicalResponse response = nlpService.extractMedicalData(transcription);

        List<String> diseases = cleanList(response.getDiseases());
        List<String> symptoms = cleanList(response.getSymptoms());

        // ✅ 3. ICD Mapping
        EHR ehr = new EHR();
        String icdCode = icdService.getICDCode(diseases);
        ehr.setIcdCode(icdCode);


        // ✅ 4. Save EHR
        ehr.setPatientName("Auto Generated");
        ehr.setDiagnosis(String.join(", ", diseases));
        ehr.setSymptoms(String.join(", ", symptoms));
        ehr.setIcdCode(icdCode);

        return ehrRepository.save(ehr);
    }

    // ✅ EXISTING METHOD (keep it)
    public EHR saveRecord(EHR ehr) {

        MedicalResponse response = nlpService.extractMedicalData(ehr.getDiagnosis());

        if (response != null) {

            if (response.getDiseases() != null && !response.getDiseases().isEmpty()) {
                ehr.setDiagnosis(String.join(", ", cleanList(response.getDiseases())));
            }

            if (response.getSymptoms() != null) {
                ehr.setSymptoms(String.join(", ", cleanList(response.getSymptoms())));
            }

            if (response.getMedications() != null) {
                ehr.setMedications(String.join(", ", response.getMedications()));
            }

            // 🔥 ADD ICD HERE ALSO
            String icdCode = icdService.searchICD(cleanList(response.getDiseases()).toString()).toString();
            ehr.setIcdCode(icdCode);
        }

        return ehrRepository.save(ehr);
    }

    public List<EHR> getAllRecords() {
        return ehrRepository.findAll();
    }

    public List<EHR> getPatientRecords(Long patientId) {
        return ehrRepository.findByPatientId(patientId);
    }

    // 🔧 CLEANING (VERY IMPORTANT FOR ICD MATCHING)
    private List<String> cleanList(List<String> list) {
        if (list == null) return List.of();

        return list.stream()
                .map(s -> s.replaceAll("\"", "").trim().toLowerCase())
                .map(this::normalizeDisease)
                .distinct()
                .collect(Collectors.toList());
    }

    // 🔧 NORMALIZATION (fix your ICD issue 🔥)
    private String normalizeDisease(String disease) {

        if (disease.contains("cholera")) return "cholera";
        if (disease.contains("diabetes")) return "diabetes";
        if (disease.contains("hypertension")) return "hypertension";
        if (disease.contains("fever")) return "fever";

        return disease;
    }
}