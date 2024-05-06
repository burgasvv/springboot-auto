package com.burgas.springbootauto.entity.car;

import com.burgas.springbootauto.entity.engine.Engine;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Equipment {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @OneToOne(mappedBy = "equipment", cascade = CascadeType.ALL)
    private Engine engine;
}
