package com.carebridge.controller;

import com.carebridge.entity.EHR;
import com.carebridge.service.EHRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ehr")
public class EHRController {

    @Autowired
    private EHRService ehrService;

    // 🔹 SAVE RECORD (NLP will run automatically)
    @PostMapping("/save")
    public EHR saveRecord(@RequestBody EHR ehr) {
        return ehrService.saveRecord(ehr);
    }

    // 🔹 GET ALL RECORDS
    @GetMapping("/all")
    public List<EHR> getAllRecords() {
        return ehrService.getAllRecords();
    }

    // 🔹 GET BY PATIENT ID
    @GetMapping("/patient/{id}")
    public List<EHR> getPatientRecords(@PathVariable Long id) {
        return ehrService.getPatientRecords(id);
    }
}