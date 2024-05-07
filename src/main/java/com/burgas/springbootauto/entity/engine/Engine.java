package com.burgas.springbootauto.entity.engine;

import com.burgas.springbootauto.entity.car.Equipment;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
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

    @ManyToOne
    @JoinColumn(name = "edition_id", referencedColumnName = "id")
    private EngineEdition engineEdition;

    @ManyToOne
    @JoinColumn(name = "fuel_id", referencedColumnName = "id")
    private Fuel fuel;

    @OneToOne
    @JoinColumn(name = "equipment_id", referencedColumnName = "id")
    private Equipment equipment;

    @OneToOne(mappedBy = "engine", cascade = CascadeType.ALL)
    private EngineCharacteristics engineCharacteristics;
}
