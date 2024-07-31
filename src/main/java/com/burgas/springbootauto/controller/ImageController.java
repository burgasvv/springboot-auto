package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.entity.image.Image;
import com.burgas.springbootauto.entity.image.ImageForm;
import com.burgas.springbootauto.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Flux;

import java.io.ByteArrayInputStream;
import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

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

    @GetMapping("/view/{id}")
    public ModelAndView viewImage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("images/view");
        modelAndView.addObject("image", imageService.findById(id));
        return modelAndView;
    }

    @GetMapping
    public CollectionModel<EntityModel<ImageForm>> getAllImages() {
        return CollectionModel.of(
                imageService.findAll().stream().map(image ->
                    EntityModel.of(
                            ImageForm.builder().id(image.getId()).name(image.getName()).isPreview(image.isPreview()).build(),
                            linkTo(methodOn(ImageController.class).viewImage(image.getId())).withSelfRel()
                    )
                ).toList()
        );
    }

    @GetMapping("/all")
    public Flux<EntityModel<ImageForm>> getFluxImages() {
        return Flux.fromIterable(
                imageService.findAll().stream().map(image -> EntityModel.of(
                        ImageForm.builder().name(image.getName()).isPreview(image.isPreview()).build(),
                        linkTo(methodOn(ImageController.class).viewImage(image.getId())).withSelfRel()
                )).toList()
        );
    }
}
