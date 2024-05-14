package com.burgas.springbootauto.entity.engine;

import com.burgas.springbootauto.entity.car.Equipment;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Engine {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToOne
    @JoinColumn(name = "edition_id", referencedColumnName = "id")
    private EngineEdition engineEdition;

    @ManyToOne
    @JoinColumn(name = "fuel_id", referencedColumnName = "id")
    private Fuel fuel;

    @OneToMany(mappedBy = "engine", cascade = CascadeType.ALL)
    private List<Equipment> equipments = new ArrayList<>();

    @OneToOne(mappedBy = "engine", cascade = CascadeType.ALL)
    private EngineCharacteristics engineCharacteristics;

    public void addEquipment(Equipment equipment) {
        equipments.add(equipment);
        equipment.setEngine(this);
    }

    public void removeEquipment(Equipment equipment) {
        equipments.remove(equipment);
        equipment.setEngine(null);
    }

    public void removeEquipments(List<Equipment> equipments) {
        this.equipments.removeAll(equipments);
        equipments.forEach(equipment -> equipment.setEngine(null));
    }

    public void addEngineCharacteristics(EngineCharacteristics engineCharacteristics) {
        this.engineCharacteristics = engineCharacteristics;
        engineCharacteristics.setEngine(this);
    }

    public void removeEngineCharacteristics(EngineCharacteristics engineCharacteristics) {
        this.engineCharacteristics = null;
        engineCharacteristics.setEngine(null);
    }
}
