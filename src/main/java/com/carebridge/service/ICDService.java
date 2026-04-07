package com.carebridge.service;

import com.carebridge.entity.ICDEntry;
import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public String getICDCode(List<String> diseases) {

        Set<String> codes = new HashSet<>();

        for (String disease : diseases) {

            List<ICDEntry> matches = searchICD(disease);

            for (ICDEntry entry : matches) {
                codes.add(entry.getCode());   // 🔥 extract code only
            }
        }

        return String.join(", ", codes);
    }
}