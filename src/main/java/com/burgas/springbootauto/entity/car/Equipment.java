package com.burgas.springbootauto.entity.car;

import com.burgas.springbootauto.entity.engine.Engine;
import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.entity.transmission.Transmission;
import com.burgas.springbootauto.entity.turbocharging.Turbocharger;
import com.burgas.springbootauto.calculations.EquipmentDataProcessing;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Equipment {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String image;

    @Column(nullable = false)
    private boolean attached;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "engine_id")
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
    @JoinColumn(name = "transmission_id")
    private Transmission transmission;

    public void addTransmission(Transmission transmission) {
        transmission.getEquipments().add(this);
        this.transmission = transmission;
    }

    public void removeTransmission(Transmission transmission) {
        transmission.getEquipments().remove(this);
        this.transmission = null;
    }

    @ManyToOne
    @JoinColumn(name = "turbocharger_id")
    private Turbocharger turbocharger;

    public void addTurbocharger(Turbocharger turbocharger) {
        turbocharger.getEquipments().add(this);
        this.turbocharger = turbocharger;
    }

    public void removeTurbocharger(Turbocharger turbocharger) {
        turbocharger.getEquipments().remove(this);
        this.turbocharger = null;
    }

    public String getAcceleration() {
        EquipmentDataProcessing equipmentDataProcessing = new EquipmentDataProcessing(this);
        if (equipmentDataProcessing.acceleration() == null) {
            return null;
        }
        return equipmentDataProcessing.acceleration();
    }

    public String getMaxSpeed() {
        EquipmentDataProcessing equipmentDataProcessing = new EquipmentDataProcessing(this);
        if (equipmentDataProcessing.maxSpeed() == null) {
            return null;
        }
        return String.valueOf(equipmentDataProcessing.maxSpeed());
    }
}
