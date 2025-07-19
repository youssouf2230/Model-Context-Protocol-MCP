package net.youssouf.mcpclient.agent;

import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AIAgent {

    private static final String OLLAMA_API_URL = "http://localhost:11434/api/generate";
    private final RestTemplate restTemplate = new RestTemplate();

    public String askLLM(String query) {
        if (query == null || query.trim().isEmpty()) {
            return "La requête est vide.";
        }

        try {
            System.out.println("Question posée : " + query);

            // Corps JSON pour l'API Ollama
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "llama2");
            requestBody.put("prompt", query);
            requestBody.put("stream", false);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            // Appel à l'API Ollama
            ResponseEntity<Map> response = restTemplate.postForEntity(OLLAMA_API_URL, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Object result = response.getBody().get("response");
                if (result != null) {
                    String texte = result.toString().trim();
                    System.out.println("Réponse IA : " + texte);
                    return texte;
                }
            }

            return "Réponse vide ou inattendue de l'IA.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur lors de l'appel à Ollama : " + e.getMessage();
        }
    }
}
