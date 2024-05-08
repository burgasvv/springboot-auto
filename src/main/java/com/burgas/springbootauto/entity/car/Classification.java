package com.burgas.springbootauto.entity.car;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Classification {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String image;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "classification", cascade = CascadeType.ALL)
    private List<Car>cars = new ArrayList<>();

    public void addCar(Car car) {
        cars.add(car);
        car.setClassification(this);
    }

    public void removeCar(Car car) {
        cars.remove(car);
        car.setClassification(null);
    }
}
