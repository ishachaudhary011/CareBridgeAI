package com.carebridge.controller;

import com.carebridge.entity.ICDEntry;
import com.carebridge.service.ICDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/icd")
public class ICDController {

    @Autowired
    private ICDService icdService;

    // 🔍 Search ICD by disease
    @GetMapping("/search")
    public List<ICDEntry> searchICD(@RequestParam String disease) {
        return icdService.searchICD(disease);
    }
}