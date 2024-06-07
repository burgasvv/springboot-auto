package com.burgas.springbootauto.service.car;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.image.Image;
import com.burgas.springbootauto.repository.car.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public Page<Car> findPage(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size).withSort(Sort.by(Sort.Direction.DESC, "title"));
        return carRepository.findAll(pageable);
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car findById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    public Page<Car>findCarsByBrandId(@NotNull Long brandId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size).withSort(Sort.by(Sort.Direction.DESC, "title"));
        return carRepository.findCarsByBrandId(brandId, pageRequest);
    }

    public Page<Car>findCarsByClassificationId(@NotNull Long classId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size).withSort(Sort.by(Sort.Direction.DESC, "title"));
        return carRepository.findCarsByClassificationId(classId, pageRequest);
    }

    public Page<Car>findCarsByCategoryId(@NotNull Long categoryId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size).withSort(Sort.by(Sort.Direction.DESC, "title"));
        return carRepository.findCarsByCategoryId(categoryId, pageRequest);
    }

    public Page<Car>findCarsByPersonId(Long personId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size).withSort(Sort.by(Sort.Direction.DESC, "title"));
        return carRepository.findCarsByPersonId(personId, pageRequest);
    }

    public Page<Car>searchUserCarsByCategoryAndClassificationAndBrand(String username, String search, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size).withSort(Sort.by(Sort.Direction.DESC, "title"));
        return carRepository.searchUserCarsByCategoryAndClassificationAndBrand(username, search, pageRequest);
    }

    public Page<Car> searchCarsByTagName(String tagName, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size).withSort(Sort.by(Sort.Direction.DESC, "title"));
        return carRepository.searchCarsByTagName(tagName, pageRequest);
    }

    public Page<Car> searchTagCarsByClassificationAndAndCategoryNoSpaces(String tag, String search, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size).withSort(Sort.by(Sort.Direction.DESC, "title"));
        return carRepository.searchTagCarsByClassificationAndAndCategoryNoSpaces(tag, search, pageRequest);
    }

    public Page<Car> searchCarsByClassificationAndAndCategoryNoSpaces(String search, int page, int size) {
        PageRequest pageable = PageRequest.of(page - 1, size).withSort(Sort.by(Sort.Direction.DESC, "title"));
        return carRepository.searchCarsByClassificationAndAndCategoryNoSpaces(search, pageable);
    }

    public Page<Car> searchCarsByKeyword(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size).withSort(Sort.by(Sort.Direction.DESC, "title"));
        return carRepository.searchCarsWithNoSpaces(keyword, pageable);
    }

    public List<Car> searchCarsByAllNames(String search) {
        return carRepository.searchCarsByAllNames(search).stream().distinct().toList();
    }

    @SneakyThrows
    @Transactional
    public void create(Car car, MultipartFile multipartFile) {
        if (multipartFile.getSize() != 0) {
            Image image = new Image();
            image.setName(multipartFile.getOriginalFilename());
            image.setPreview(true);
            image.setData(multipartFile.getBytes());
            car.addImage(image);
            car.setHasPreview(true);
        }
        carRepository.save(car);
    }

    @SneakyThrows
    @Transactional
    public void changePreviewImage(Car car, MultipartFile multipartFile) {
        if (multipartFile.getSize() != 0) {
            car.getImages().stream().filter(Image::isPreview).forEach(image -> image.setPreview(false));
            Image image = new Image();
            image.setName(multipartFile.getOriginalFilename());
            image.setPreview(true);
            image.setData(multipartFile.getBytes());
            car.addImage(image);
            car.setHasPreview(true);
            carRepository.save(car);
        }
    }

    @Transactional
    public void removePreviewImage(Car car) {
        car.getImages().stream().filter(Image::isPreview).forEach(image -> image.setPreview(false));
        car.setHasPreview(false);
        carRepository.save(car);
    }

    @SneakyThrows
    @Transactional
    public void addImages(Car car, MultipartFile[] files) {
        for (MultipartFile file : files) {
            Image image = new Image();
            image.setName(UUID.randomUUID().toString());
            image.setPreview(false);
            image.setData(file.getBytes());
            car.addImage(image);
            carRepository.save(car);
        }
    }

    @Transactional
    public void save(Car car) {
        carRepository.save(car);
    }

    @Transactional
    public void update(Car car) {
        carRepository.save(car);
    }

    @Transactional
    public void delete(Long id) {
        carRepository.deleteById(id);
    }
}
