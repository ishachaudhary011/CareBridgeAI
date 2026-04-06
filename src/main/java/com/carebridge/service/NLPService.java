package com.carebridge.service;

import com.carebridge.DTO.MedicalResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Service
public class NLPService {

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String apiKey;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MedicalResponse extractMedicalData(String text) {

        RestTemplate restTemplate = new RestTemplate();

        String prompt = """
        Extract diseases, symptoms, and medications from the text.
        Return strictly JSON:
        {
          "diseases": [],
          "symptoms": [],
          "medications": []
        }

        Text: """ + text;

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-5-mini");
        body.put("input", prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response =
                    restTemplate.postForEntity(apiUrl, request, String.class);

            // 🔥 Extract text from OpenAI response
            JsonNode root = objectMapper.readTree(response.getBody());

            String content = root
                    .path("output")
                    .get(0)
                    .path("content")
                    .get(0)
                    .path("text")
                    .asText();

            // 🔥 Convert JSON string → MedicalResponse
            return objectMapper.readValue(content, MedicalResponse.class);

        } catch (Exception e) {
            System.out.println("NLP Error: " + e.getMessage());
            return new MedicalResponse(); // fail-safe
        }
    }
}