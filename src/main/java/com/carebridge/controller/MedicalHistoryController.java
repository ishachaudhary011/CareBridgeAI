package com.carebridge.controller;

import com.carebridge.entity.MedicalHistory;
import com.carebridge.service.MedicalHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
public class MedicalHistoryController {

    @Autowired
    private MedicalHistoryService medicalHistoryService;

    @PostMapping
    public MedicalHistory addHistory(@RequestBody MedicalHistory history) {
        return medicalHistoryService.saveMedicalHistory(history);
    }

    @GetMapping("/patient/{id}")
    public List<MedicalHistory> getHistory(@PathVariable Long id) {
        return medicalHistoryService.getHistoryByPatientId(id);
    }
}