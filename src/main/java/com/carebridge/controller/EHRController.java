package com.carebridge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.carebridge.entity.EHR;
import com.carebridge.service.EHRService;

@RestController
@RequestMapping("/ehr")
public class EHRController {

    @Autowired
    private EHRService ehrService;

    @PostMapping
    public EHR addRecord(@RequestBody EHR ehr){
        return ehrService.saveRecord(ehr);
    }

    @GetMapping
    public List<EHR> getAllRecords(){
        return ehrService.getAllRecords();
    }

    @GetMapping("/patient/{id}")
    public List<EHR> getPatientRecords(@PathVariable Long id){
        return ehrService.getPatientRecords(id);
    }
}