package com.aditya.Java_backend.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class ApiService {

    @Value("${groq.api.key}")
    private String apiKey;

    public String fetchData(String code) {

        try {
            if (apiKey == null) return getMock();

            RestTemplate rt = new RestTemplate();

            HttpHeaders h = new HttpHeaders();
            h.setContentType(MediaType.APPLICATION_JSON);
            h.setBearerAuth(apiKey);

            Map<String, Object> body = Map.of(
                    "model", "llama-3.3-70b-versatile",
                    "max_tokens", 1000,
                    "messages", List.of(Map.of(
                            "role", "user",
                            "content", "Review this code:\n\n" + code
                    ))
            );

            ResponseEntity<Map> res = rt.exchange(
                    "https://api.groq.com/openai/v1/chat/completions",
                    HttpMethod.POST,
                    new HttpEntity<>(body, h),
                    Map.class
            );

            return (String) ((Map)((Map)((List)res.getBody()
                    .get("choices")).get(0)).get("message"))
                    .get("content");

        } catch (Exception e) {
            return getMock();
        }
    }

    private String getMock() {
        return "Mock Review: Configure GROQ_API_KEY";
    }
}

