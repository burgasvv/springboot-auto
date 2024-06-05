package com.burgas.springbootauto.service.image;

import com.burgas.springbootauto.entity.image.Image;
import com.burgas.springbootauto.repository.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public Image findById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    public void save(Image image) {
        imageRepository.save(image);
    }
}
