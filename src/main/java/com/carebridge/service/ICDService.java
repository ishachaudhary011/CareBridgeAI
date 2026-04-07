package com.carebridge.service;

import com.carebridge.entity.ICDEntry;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import com.opencsv.CSVReader;

@Service
public class ICDService {

    private List<ICDEntry> icdList = new ArrayList<>();

    @PostConstruct
    public void loadICDData() {
        try (CSVReader reader = new CSVReader(new FileReader("icd10codes.csv"))) {

            String[] parts;
            reader.readNext(); // skip header

            while ((parts = reader.readNext()) != null) {

                String code = parts[2].trim();
                String detailed = parts[3].toLowerCase().trim();
                String shortName = parts[5].toLowerCase().trim();

                icdList.add(new ICDEntry(code, detailed, shortName));
            }

            System.out.println("✅ Loaded: " + icdList.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String normalize(String text) {
        return text.toLowerCase().replaceAll("[^a-zA-Z ]", "").trim();
    }

    public List<ICDEntry> searchICD(String disease) {
        disease = normalize(disease);

        List<ICDEntry> results = new ArrayList<>();

        for (ICDEntry entry : icdList) {

            // ✅ Match using short name
            if (entry.getShortName().contains(disease)) {
                results.add(entry);
            }

            // ✅ Match using detailed description
            else if (entry.getDetailedDescription().contains(disease)) {
                results.add(entry);
            }
        }

        return results.size() > 3 ? results.subList(0, 3) : results;
    }
}