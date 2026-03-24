package com.carebridge.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class NLPService {

    private static final Map<String, String> symptomToDiagnosis = new HashMap<>();
    static {
        symptomToDiagnosis.put("fever", "Fever");
        symptomToDiagnosis.put("cough", "Cough");
        symptomToDiagnosis.put("headache", "Headache");
        symptomToDiagnosis.put("vomiting", "Vomiting");
        symptomToDiagnosis.put("diabetes", "Diabetes");
        symptomToDiagnosis.put("hypertension", "Hypertension");
    }

    private static final Map<String, String> diagnosisToICD = new HashMap<>();
    static {
        diagnosisToICD.put("Fever", "MG26");
        diagnosisToICD.put("Cough", "MD12");
        diagnosisToICD.put("Headache", "8A80");
        diagnosisToICD.put("Vomiting", "MD90");
        diagnosisToICD.put("Diabetes", "5A11");
        diagnosisToICD.put("Hypertension", "BA00");
    }

    public Map<String, Object> extractData(String text) {
        if (text == null) text = "";
        text = text.toLowerCase();

        List<String> symptoms = new ArrayList<>();
        Set<String> diagnosesSet = new LinkedHashSet<>();
        Set<String> icdSet = new LinkedHashSet<>();

        for (String key : symptomToDiagnosis.keySet()) {
            if (text.contains(key)) {
                symptoms.add(key);
                String disease = symptomToDiagnosis.get(key);
                diagnosesSet.add(disease);
                String icd = diagnosisToICD.getOrDefault(disease, "UNKNOWN");
                icdSet.add(icd);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("symptoms", symptoms.isEmpty() ? null : String.join(", ", symptoms));
        result.put("diagnosis", diagnosesSet.isEmpty() ? "Not Found" : String.join(", ", diagnosesSet));
        result.put("icdCode", icdSet.isEmpty() ? "UNKNOWN" : String.join(", ", icdSet));

        // Debug print
        System.out.println("Detected Symptoms: " + symptoms);
        System.out.println("Diagnosis: " + result.get("diagnosis"));
        System.out.println("ICD Codes: " + result.get("icdCode"));

        return result;
    }
}