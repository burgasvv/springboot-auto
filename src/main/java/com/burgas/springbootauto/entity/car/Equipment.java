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

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "engine_id", referencedColumnName = "id")
    private Engine engine;

    public void addEngine(Engine engine) {
        engine.getEquipments().add(this);
        this.engine = engine;
    }

    public void removeEngine(Engine engine) {
        engine.getEquipments().remove(this);
        this.engine = null;
    }

    @ManyToOne
    @JoinColumn(name = "transmission_id", referencedColumnName = "id")
    private Transmission transmission;

    public void addTransmission(Transmission transmission) {
        transmission.getEquipments().add(this);
        this.transmission = transmission;
    }

    public void removeTransmission(Transmission transmission) {
        transmission.getEquipments().remove(this);
        this.transmission = null;
    }
}
