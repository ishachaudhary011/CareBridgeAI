package com.carebridge.service;

import com.carebridge.DTO.MedicalResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class NLPService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value("${sambanova.api.url}")
    private String apiUrl;
    @Value("${sambanova.api.key}")
    private String apiKey;

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
                
                Text: %s
                """.formatted(text);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "Meta-Llama-3.1-8B-Instruct");
        body.put("stream", false); // 🔥 IMPORTANT FIX

        // ✅ Correct messages format
        body.put("messages", new Object[]{
                Map.of("role", "system", "content", "You are a medical AI assistant."),
                Map.of("role", "user", "content", prompt)
        });

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response =
                    restTemplate.postForEntity(apiUrl, request, String.class);

            System.out.println("RAW RESPONSE: " + response.getBody()); // 🔥 DEBUG

            JsonNode root = objectMapper.readTree(response.getBody());

            // ✅ Correct parsing (SambaNova format)
            String content = root
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

            // 🔥 Clean JSON (important)
            content = content.replaceAll("```json", "")
                    .replaceAll("```", "")
                    .trim();

            return objectMapper.readValue(content, MedicalResponse.class);

        } catch (Exception e) {
            System.out.println("NLP Error: " + e.getMessage());
            return new MedicalResponse(); // fail-safe
        }
    }
}