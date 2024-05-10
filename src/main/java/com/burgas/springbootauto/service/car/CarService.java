package com.burgas.springbootauto.service.car;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.repository.car.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> findAll() {
        return carRepository.findAll().stream().toList();
    }

    public Car findById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    public List<Car> searchCarsByCategoryId(Long id) {
        return carRepository.searchCarsByCategoryId(id);
    }

    public List<Car> searchCarByClassificationId(Long id) {
        return carRepository.searchCarsByClassificationId(id);
    }

    public List<Car> searchCarsByTagName(String tagName) {
        return carRepository.searchCarsByTagName(tagName).stream().distinct().toList();
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
