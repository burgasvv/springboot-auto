package com.burgas.springbootauto.service.image;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.image.Image;
import com.burgas.springbootauto.repository.car.CarRepository;
import com.burgas.springbootauto.repository.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final CarRepository carRepository;

    public List<Image> findAll() {
        return imageRepository.findAll();
    }

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

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void deletePreview(Long carId, Long imageId) {
        Car car = carRepository.findById(carId).orElse(null);
        Image image = imageRepository.findById(imageId).orElse(null);
        if (Objects.requireNonNull(image).isPreview()) {
            Objects.requireNonNull(car).setHasPreview(false);
        }
        carRepository.save(Objects.requireNonNull(car));
        imageRepository.deleteById(image.getId());
    }
}
