package com.carebridge.controller;

import com.carebridge.entity.Prescription;
import com.carebridge.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @PostMapping
    public Prescription addPrescription(@RequestBody Prescription prescription) {
        return prescriptionService.savePrescription(prescription);
    }

    @GetMapping
    public List<Prescription> getAllPrescriptions() {
        return prescriptionService.getAllPrescriptions();
    }

    @GetMapping("/ehr/{id}")
    public List<Prescription> getPrescriptions(@PathVariable Long id) {
        return prescriptionService.getPrescriptionsByEhrId(id);
    }
}