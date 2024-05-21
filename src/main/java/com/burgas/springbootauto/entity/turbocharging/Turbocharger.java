package com.burgas.springbootauto.entity.turbocharging;

import com.burgas.springbootauto.entity.brand.Brand;
import com.burgas.springbootauto.entity.car.Equipment;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Turbocharger {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String image;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @Column(nullable = false)
    private String powerIntake;

    @Column(nullable = false)
    private String powerGeneration;

    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToOne
    @JoinColumn(name = "turbotype_id", referencedColumnName = "id")
    private TurboType turboType;

    @OneToMany(mappedBy = "turbocharger", cascade = CascadeType.ALL)
    private List<Equipment> equipments = new ArrayList<>();

    public void removeEquipments(List<Equipment> equipments) {
        this.equipments.removeAll(equipments);
        equipments.forEach(equipment -> equipment.setTurbocharger(null));
    }
}
