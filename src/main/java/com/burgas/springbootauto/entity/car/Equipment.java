package com.burgas.springbootauto.entity.car;

import com.burgas.springbootauto.entity.engine.Engine;
import com.burgas.springbootauto.entity.transmission.Transmission;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "engine_id", referencedColumnName = "id")
    private Engine engine;

    @ManyToOne
    @JoinColumn(name = "transmission_id", referencedColumnName = "id")
    private Transmission transmission;
}
