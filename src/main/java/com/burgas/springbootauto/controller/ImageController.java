package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.image.Image;
import com.burgas.springbootauto.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getImage(@PathVariable Long id) {
        Image image = imageService.findById(id);
        return ResponseEntity.ok(
                new InputStreamResource(
                        new ByteArrayInputStream(Objects.requireNonNull(image).getData()))
        );
    }
}
