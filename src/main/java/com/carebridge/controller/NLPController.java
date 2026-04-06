package com.carebridge.controller;

import com.carebridge.DTO.MedicalRequest;
import com.carebridge.DTO.MedicalResponse;
import com.carebridge.service.NLPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nlp")
public class NLPController {

    @Autowired
    private NLPService nlpService;

    @PostMapping("/extract")
    public MedicalResponse extract(@RequestBody MedicalRequest request) {
        return nlpService.extractMedicalData(request.getText());
    }
}
