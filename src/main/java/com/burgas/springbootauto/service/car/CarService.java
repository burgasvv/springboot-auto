package com.burgas.springbootauto.service.car;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.repository.car.CarRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<Car> searchCarsByTagName(String tagName) {
        return carRepository.searchCarsByTagName(tagName).stream().distinct().toList();
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
