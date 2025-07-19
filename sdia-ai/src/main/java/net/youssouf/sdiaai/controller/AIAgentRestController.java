package net.youssouf.sdiaai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class AIAgentRestController {
    // permet de communiquer avec n'importe quel LLM
    private ChatClient chatClient;
    public AIAgentRestController(ChatClient.Builder builder, ChatMemory memory) {
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor()) // permet d'env message sur console
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build()) // memoriser histor
                .build();
    }

    @GetMapping("/chat")
    public String askLLM(@RequestParam String message) {
        return chatClient.prompt()
                .system("Colorer toujours des mots clés dans le chat")
                .user(message)
                .call().content();
    }
}
