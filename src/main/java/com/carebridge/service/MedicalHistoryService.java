package com.carebridge.service;

import com.carebridge.entity.MedicalHistory;
import com.carebridge.repository.MedicalHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalHistoryService {

    @Autowired
    private MedicalHistoryRepository medicalHistoryRepository;

    public MedicalHistory saveMedicalHistory(MedicalHistory history) {
        return medicalHistoryRepository.save(history);
    }

    public List<MedicalHistory> getAllMedicalHistory() {
        return medicalHistoryRepository.findAll();
    }

    public List<MedicalHistory> getHistoryByPatientId(Long patientId) {
        return medicalHistoryRepository.findByPatientId(patientId);
    }

    public MedicalHistory getHistoryById(Long id) {
        return medicalHistoryRepository.findById(id).orElse(null);
    }

    public void deleteHistory(Long id) {
        medicalHistoryRepository.deleteById(id);
    }
}