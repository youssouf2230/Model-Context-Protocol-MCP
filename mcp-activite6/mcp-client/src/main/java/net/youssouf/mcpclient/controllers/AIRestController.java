package net.youssouf.mcpclient.controllers;

import net.youssouf.mcpclient.agent.AIAgent;
import org.springframework.web.bind.annotation.*;

@RestController
public class AIRestController {

    private final AIAgent agent;

    public AIRestController(AIAgent agent) {
        this.agent = agent;
    }

    // ✅ Méthode GET pour Swagger et Postman
    @GetMapping("/chat")
    public String chat(@RequestParam String query) {
        return agent.askLLM(query);
    }
}
