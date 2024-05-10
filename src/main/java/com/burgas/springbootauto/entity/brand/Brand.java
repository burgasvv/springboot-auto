package com.burgas.springbootauto.entity.brand;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.engine.EngineEdition;
import com.burgas.springbootauto.entity.transmission.Gearbox;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Brand {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String image;

    @Column(nullable = false)
    private String website;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<EngineEdition>engineEditions = new ArrayList<>();

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Gearbox>gearboxes = new ArrayList<>();

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Car>cars = new ArrayList<>();

    public void addEngineEdition(EngineEdition engineEdition) {
        engineEditions.add(engineEdition);
        engineEdition.setBrand(this);
    }

    public void removeEngineEdition(EngineEdition engineEdition) {
        engineEditions.remove(engineEdition);
        engineEdition.setBrand(null);
    }

    public void addCar(Car car) {
        cars.add(car);
        car.setBrand(this);
    }

    public void removeCar(Car car) {
        cars.remove(car);
        car.setBrand(null);
    }
}
