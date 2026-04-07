package com.carebridge.controller;

import com.carebridge.entity.EHR;
import com.carebridge.service.EHRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ehr")
@CrossOrigin("*")
public class EHRController {

    @Autowired
    private EHRService ehrService;

    // ✅ EXISTING
    @PostMapping("/save")
    public EHR saveRecord(@RequestBody EHR ehr) {
        return ehrService.saveRecord(ehr);
    }

    // 🔥 NEW (FULL PIPELINE)
    @PostMapping(value = "/process-audio", consumes = {"multipart/form-data"})
    public EHR processAudio(@RequestParam("file") MultipartFile file) throws IOException {
        return ehrService.processAudio(file);
    }

    @GetMapping("/all")
    public List<EHR> getAllRecords() {
        return ehrService.getAllRecords();
    }

    @GetMapping("/patient/{id}")
    public List<EHR> getPatientRecords(@PathVariable Long id) {
        return ehrService.getPatientRecords(id);
    }
}