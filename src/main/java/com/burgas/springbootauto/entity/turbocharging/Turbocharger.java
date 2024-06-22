package com.burgas.springbootauto.entity.turbocharging;

import com.burgas.springbootauto.calculations.EquipmentDataProcessing;
import com.burgas.springbootauto.entity.brand.Brand;
import com.burgas.springbootauto.entity.car.Equipment;
import jakarta.persistence.*;
import lombok.*;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Turbocharger {

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
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @Column(nullable = false)
    private String rpm;

    @Column(nullable = false)
    private String torque;

    public String getPower() {
        EquipmentDataProcessing edp = new EquipmentDataProcessing();
        double power = edp.getDouble(torque) * edp.getDouble(rpm) / 9549.0 * 1.36;
        NumberFormat instance = NumberFormat.getInstance();
        instance.setMinimumFractionDigits(2);
        return instance.format(power);
    }

    @ManyToOne
    @JoinColumn(name = "turbotype_id")
    private TurboType turboType;

    @OneToMany(mappedBy = "turbocharger",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}
    )
    private List<Equipment> equipments = new ArrayList<>();

    public void removeEquipments(List<Equipment> equipments) {
        this.equipments.removeAll(equipments);
        equipments.forEach(equipment -> equipment.setTurbocharger(null));
    }
}
