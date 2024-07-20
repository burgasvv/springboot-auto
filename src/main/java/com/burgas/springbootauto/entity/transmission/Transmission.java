package com.burgas.springbootauto.entity.transmission;

import com.burgas.springbootauto.entity.brand.Brand;
import com.burgas.springbootauto.entity.car.Equipment;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transmission {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String finalRatio;

    @Column(nullable = false)
    private String ratio;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "drivetype_id")
    private DriveType driveType;

    @ManyToOne
    @JoinColumn(name = "gearbox_id")
    private Gearbox gearbox;

    @OneToMany(mappedBy = "transmission",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
    )
    private List<Equipment>equipments = new ArrayList<>();

    public void removeEquipments(List<Equipment> equipments) {
        this.equipments.removeAll(equipments);
        equipments.forEach(equipment -> equipment.setTransmission(null));
    }
}
