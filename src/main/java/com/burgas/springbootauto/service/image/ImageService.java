package com.burgas.springbootauto.service.image;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.image.Image;
import com.burgas.springbootauto.repository.image.ImageRepository;
import com.burgas.springbootauto.service.car.CarService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Page<Image> findImagesByCarId(Long carId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size).withSort(Sort.by(Sort.Direction.DESC, "id"));
        return imageRepository.findImagesByCarId(carId, pageRequest);
    }

    @Transactional
    public void save(Image image) {
        imageRepository.save(image);
    }

    @Transactional
    public Image saveNewImage(Image image) {
        return imageRepository.save(image);
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
