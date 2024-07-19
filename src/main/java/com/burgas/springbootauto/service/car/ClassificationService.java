package com.burgas.springbootauto.service.car;

import com.burgas.springbootauto.entity.car.Classification;
import com.burgas.springbootauto.entity.image.Image;
import com.burgas.springbootauto.repository.car.ClassificationRepository;
import com.burgas.springbootauto.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClassificationService {

    private final ClassificationRepository classificationRepository;
    private final ImageService imageService;

    public List<Classification> findAll() {
        return classificationRepository.findAll();
    }

    public Classification findById(Long id) {
        return classificationRepository.findById(id).orElse(null);
    }

    @SneakyThrows
    @Transactional
    public void save(Classification classification, MultipartFile file) {
        if (file.getSize() != 0) {
            Image image = Image.builder().name(file.getOriginalFilename())
                    .isPreview(true)
                    .data(file.getBytes()).build();
            imageService.save(image);
            classification.setImage(image);
        }
        classificationRepository.save(classification);
    }

    @Transactional
    public void update(Classification classification) {
        classificationRepository.save(classification);
    }

    @Transactional
    public void delete(Long id) {
        Classification classification = classificationRepository.findById(id).orElse(null);
        Objects.requireNonNull(classification).getCars().forEach(car -> car.setClassification(null));
        classificationRepository.save(classification);
        classificationRepository.deleteById(id);
    }

    @Transactional
    public void removeImage(Classification classification) {
        Image image = classification.getImage();
        classification.setImage(null);
        classificationRepository.save(classification);
        imageService.delete(image);
    }
}
