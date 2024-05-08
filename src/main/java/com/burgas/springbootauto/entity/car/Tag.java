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
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL)
    private List<Car>cars = new ArrayList<>();

    public void addCar(Car car) {
        cars.add(car);
        car.getTags().add(this);
    }

    public void removeCar(Car car) {
        cars.remove(car);
        car.getTags().remove(this);
    }
}
