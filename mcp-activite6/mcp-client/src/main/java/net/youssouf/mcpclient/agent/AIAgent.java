package net.youssouf.mcpclient.agent;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.stereotype.Service;

@Service
public class AIAgent {
    private ChatClient chatClient;

    public AIAgent(ChatClient.Builder chatClient, ToolCallbackProvider toolCallbackProvider) {
        this.chatClient = chatClient
                .defaultToolCallbacks(toolCallbackProvider)
                .defaultSystem("Answer the user question using provided tool callback")
                .build();
    }

    public String askLLM(String query) {
        if (query == null || query.trim().isEmpty()) {
            return "⚠️ La requête est vide. Veuillez entrer une question.";
        }

        try {
            System.out.println("💬 Question posée : " + query);

            String response = chatClient
                    .prompt()
                    .user(query)
                    .call()
                    .content();

            System.out.println("✅ Réponse IA : " + response);
            return response != null ? response : "⚠️ Réponse vide de l'IA.";
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Erreur lors de la réponse de l'IA : " + e.getMessage();
        }
    }



}
