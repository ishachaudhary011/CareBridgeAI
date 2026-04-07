package com.carebridge.controller;

import com.carebridge.entity.EHR;
import com.carebridge.service.EHRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ehr")
@CrossOrigin("*") // 🔥 IMPORTANT (frontend fix)
public class EHRController {

    @Autowired
    private EHRService ehrService;

    // ✅ SAVE RECORD
    @PostMapping("/save")
    public EHR saveRecord(@RequestBody EHR ehr) {
        return ehrService.saveRecord(ehr);
    }

    // ✅ GET ALL
    @GetMapping("/all")
    public List<EHR> getAllRecords() {
        return ehrService.getAllRecords();
    }

    // ✅ GET BY PATIENT ID
    @GetMapping("/patient/{id}")
    public List<EHR> getPatientRecords(@PathVariable Long id) {
        return ehrService.getPatientRecords(id);
    }
}