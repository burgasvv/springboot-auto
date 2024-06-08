package com.burgas.springbootauto.service.image;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.image.Image;
import com.burgas.springbootauto.repository.image.ImageRepository;
import com.burgas.springbootauto.service.car.CarService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final CarService carService;

    public Image findById(Long id) {
        return imageRepository.findById(id).orElse(null);
    }

    public Image findByName(String name) {
        return imageRepository.findByName(name);
    }

    @Transactional
    public void save(Image image) {
        imageRepository.save(image);
    }

    @Transactional
    public void delete(Image image) {
        imageRepository.deleteById(image.getId());
    }

    @Transactional
    public void deletePreview(Car car, Image image) {
        if (image.isPreview()) {
            car.setHasPreview(false);
        }
        carService.update(car);
        imageRepository.deleteById(image.getId());
    }
}
