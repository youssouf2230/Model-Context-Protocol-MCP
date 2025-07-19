package net.youssouf.sdiaai.controller;

import org.springframework.ai.image.ImageGeneration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageGenerationController {

//
//    private final OpenAiImageModel imageModel;
//
//    // Injection du modèle via le constructeur
//    public ImageGenerationController(OpenAiImageModel imageModel) {
//        this.imageModel = imageModel;
//    }
//
//    @PostMapping("/image")
//    public ResponseEntity<byte[]> generateImage(@RequestParam String prompt) {
//        byte[] imageBytes = imageModel.generateImage(prompt);
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.IMAGE_PNG)
//                .body(imageBytes);
//    }
}
