package com.burgas.springbootauto.service.car;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.image.Image;
import com.burgas.springbootauto.repository.car.CarRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    private static @NotNull PageRequest getPageRequest(int page, int size) {
        return PageRequest.of(page - 1, size).withSort(Sort.by(Sort.Direction.DESC, "title"));
    }

    public Page<Car> findPage(int page, int size) {
        PageRequest pageable = getPageRequest(page, size);
        return carRepository.findAll(pageable);
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Car findById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    public Page<Car>findCarsByBrandId(@NotNull Long brandId, int page, int size) {
        return carRepository.findCarsByBrandId(brandId, getPageRequest(page, size));
    }

    public Page<Car>findCarsByClassificationId(@NotNull Long classId, int page, int size) {
        return carRepository.findCarsByClassificationId(classId, getPageRequest(page, size));
    }

    public Page<Car>findCarsByCategoryId(@NotNull Long categoryId, int page, int size) {
        return carRepository.findCarsByCategoryId(categoryId, getPageRequest(page, size));
    }

    public Page<Car>findCarsByPersonId(Long personId, int page, int size) {
        return carRepository.findCarsByPersonId(personId, getPageRequest(page, size));
    }

    public Page<Car>searchUserCarsByCategoryAndClassificationAndBrand(String username, String search, int page, int size) {
        return carRepository.searchUserCarsByCategoryAndClassificationAndBrand(username, search, getPageRequest(page, size));
    }

    public Page<Car> searchCarsByTagName(String tagName, int page, int size) {
        return carRepository.searchCarsByTagName(tagName, getPageRequest(page, size));
    }

    public Page<Car> searchTagCarsByClassificationAndAndCategoryNoSpaces(String tag, String search, int page, int size) {
        PageRequest pageRequest = getPageRequest(page, size);
        return carRepository.searchTagCarsByClassificationAndAndCategoryNoSpaces(tag, search, pageRequest);
    }

    public Page<Car> searchCarsByClassificationAndAndCategoryNoSpaces(String search, int page, int size) {
        return carRepository.searchCarsByClassificationAndAndCategoryNoSpaces(search, getPageRequest(page, size));
    }

    public Page<Car> searchCategoryCarsByBrandAndClassificationNoSpaces(String category, String search, int page, int size) {
        return carRepository.searchCategoryCarsByBrandAndClassificationNoSpaces(category, search, getPageRequest(page, size));
    }

    public Page<Car> searchClassificationCarsByBrandAndCategory(String classification, String search,
                                                                int page, int size) {
        return carRepository.searchClassificationCarsByBrandAndCategory(classification, search, getPageRequest(page, size));
    }

    public Page<Car>findCarsByDriveUnitId(@NotNull Long driveUnitId, int page, int size) {
        return carRepository.findCarsByDriveUnitId(driveUnitId, getPageRequest(page, size));
    }

    public Page<Car> searchDriveUnitCarsByBrandAndClassificationAndCategory(String driveUnit, String search,
                                                                            int page, int size) {
        return carRepository.searchDriveUnitCarsByBrandAndClassificationAndCategory(driveUnit, search, getPageRequest(page, size));
    }

    public Page<Car> searchCarsByKeyword(String keyword, int page, int size) {
        return carRepository.searchCarsWithNoSpaces(keyword, getPageRequest(page, size));
    }

    public List<Car> searchCarsByAllNames(String search) {
        return carRepository.searchCarsByAllNames(search).stream().distinct().toList();
    }

    @SneakyThrows
    @Transactional
    public void create(Car car, MultipartFile multipartFile) {
        car.setHasPreview(false);
        if (multipartFile.getSize() != 0) {
            Image image = new Image();
            image.setName(multipartFile.getOriginalFilename() + UUID.randomUUID());
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
            image.setName(multipartFile.getOriginalFilename() + UUID.randomUUID());
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
            image.setName(file.getOriginalFilename() + UUID.randomUUID());
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

    @Transactional
    public void setPreviewImage(Car car, Image image) {
        if (!car.isHasPreview()){
            car.setHasPreview(true);
        }
        car.getImages().stream().filter(Image::isPreview).forEach(im -> im.setPreview(false));
        car.getImages().stream().filter(im -> im.equals(image)).forEach(im -> im.setPreview(true));
        carRepository.save(car);
    }
}
