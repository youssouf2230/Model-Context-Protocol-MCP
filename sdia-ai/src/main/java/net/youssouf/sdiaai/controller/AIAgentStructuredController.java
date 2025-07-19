package net.youssouf.sdiaai.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.youssouf.sdiaai.outputs.MovieListe;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class AIAgentStructuredController {

    private final ChatClient chatClient;

    public AIAgentStructuredController(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor()) // Affiche les requêtes/réponses dans la console
                .build();
    }
    private String extractJson(String raw) {
        int start = raw.indexOf('{');
        int end = raw.lastIndexOf('}');
        if (start != -1 && end != -1 && end > start) {
            return raw.substring(start, end + 1);
        }
        throw new RuntimeException("Réponse invalide, JSON non trouvé : " + raw);
    }

    @GetMapping("/askAgent")
    public MovieListe askLLM(@RequestParam String message) {
        String systemMessage = """
        Vous êtes un expert en cinéma.
        Répondez uniquement avec un objet JSON strictement au format demandé.
        Ne précédez pas la réponse de texte, ni d'introduction, ni de balises.
    """;

        String userPrompt = """
        Voici ma question : "%s"

        Réponds uniquement avec ce format JSON :
        {
          "movies": [
            {
              "title": "Titre du film",
              "director": "Nom du réalisateur",
              "genre": "Genre",
              "year": "Année"
            },
            ...
          ]
        }
    """.formatted(message);

        // 1. Obtenir la réponse brute
        String rawResponse = chatClient.prompt()
                .system(systemMessage)
                .user(userPrompt)
                .call()
                .content(); // réponse texte du LLM

        // 2. Extraire le bloc JSON proprement
        String json = extractJson(rawResponse);

        // 3. Convertir en MovieListe
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, MovieListe.class);
        } catch (Exception e) {
            throw new RuntimeException("Erreur de parsing JSON :\n" + rawResponse, e);
        }
    }

}
