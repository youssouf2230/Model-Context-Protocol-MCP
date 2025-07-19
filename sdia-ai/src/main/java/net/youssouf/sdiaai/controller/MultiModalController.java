package net.youssouf.sdiaai.controller;


import net.youssouf.sdiaai.outputs.cinModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;


@RestController
public class MultiModalController {
    private ChatClient chatClient;
    @Value("classpath:/images/img_1.png")
    private Resource image;

    public MultiModalController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }
    @GetMapping("/describe")
    public cinModel describeImage() {

        return chatClient.prompt()
                .system("Fournir moi une description de l'image")
                .user(
                        u->u.text("Decrire moi cette image")
                                .media(MediaType.IMAGE_PNG,image)
                )
                .call()
                .entity(cinModel.class);
    }
    @PostMapping(value = "/ask", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String askImage(@RequestParam("file") MultipartFile file,
                           @RequestParam("query") String query) throws IOException {

        byte[] imageBytes = file.getBytes();

        return chatClient.prompt()
                .system("Réponds à la question de l'utilisateur à propos de l'image.")
                .user(u -> u
                        .text(query)
                        .media(MediaType.IMAGE_JPEG, new ByteArrayResource(imageBytes)))
                .call()
                .content();
    }

}
