package net.youssouf.sdiaai.controller;

import io.swagger.v3.oas.models.media.MediaType;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@CrossOrigin(origins = "*")
public class StreamingController {
    private ChatClient chatClient;
    public StreamingController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @GetMapping("/stream")
    public Flux<String> stream(@RequestParam String query) {
        return chatClient.prompt()
                //.system("")
                .user(query)
                .stream()
                .content();
    }
    @GetMapping("/nostream")
    public String nostream(@RequestParam String query) {
        return chatClient.prompt()
                //.system("")
                .user(query)
                .call()
                .content();
    }
}
