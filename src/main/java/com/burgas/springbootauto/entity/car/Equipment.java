package com.burgas.springbootauto.entity.car;

import com.burgas.springbootauto.entity.engine.Engine;
import com.burgas.springbootauto.entity.transmission.Transmission;
import com.burgas.springbootauto.entity.turbocharging.Turbocharger;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@EqualsAndHashCode
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

    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToOne
    @JoinColumn(name = "turbocharger_id", referencedColumnName = "id")
    private Turbocharger turbocharger;

    public void addTurbocharger(Turbocharger turbocharger) {
        turbocharger.getEquipments().add(this);
        this.turbocharger = turbocharger;
    }

    public void removeTurbocharger(Turbocharger turbocharger) {
        turbocharger.getEquipments().remove(this);
        this.turbocharger = null;
    }
}
