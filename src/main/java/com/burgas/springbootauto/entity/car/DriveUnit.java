package com.burgas.springbootauto.entity.car;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriveUnit {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "driveUnit", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Car> cars = new ArrayList<>();

    public void addCar(Car car) {
        cars.add(car);
        car.setDriveUnit(this);
    }

    public void addCars(List<Car> cars) {
        this.cars.addAll(cars);
        cars.forEach(this::addCar);
    }

    public void removeCar(Car car) {
        cars.remove(car);
        car.setDriveUnit(null);
    }

    public void removeCars(List<Car> cars) {
        this.cars.removeAll(cars);
        cars.forEach(this::removeCar);
    }
}
