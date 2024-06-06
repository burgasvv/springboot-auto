package com.burgas.springbootauto.entity.engine;

import com.burgas.springbootauto.entity.car.Equipment;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Engine {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String image;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "edition_id")
    private EngineEdition engineEdition;

    @ManyToOne
    @JoinColumn(name = "fuel_id")
    private Fuel fuel;

    @OneToMany(mappedBy = "engine", fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}
    )
    private List<Equipment> equipments = new ArrayList<>();

    @OneToOne(mappedBy = "engine", cascade = CascadeType.ALL)
    private EngineCharacteristics engineCharacteristics;

    public void removeEquipments(List<Equipment> equipments) {
        this.equipments.removeAll(equipments);
        equipments.forEach(equipment -> equipment.setEngine(null));
    }

    public void addEngineCharacteristics(EngineCharacteristics engineCharacteristics) {
        this.engineCharacteristics = engineCharacteristics;
        engineCharacteristics.setEngine(this);
    }
}
