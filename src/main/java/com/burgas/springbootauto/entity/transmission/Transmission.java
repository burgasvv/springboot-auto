package com.burgas.springbootauto.entity.transmission;

import com.burgas.springbootauto.entity.brand.Brand;
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
public class Transmission {

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

    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToOne
    @JoinColumn(name = "drivetype_id", referencedColumnName = "id")
    private DriveType driveType;

    @ManyToOne
    @JoinColumn(name = "gearbox_id", referencedColumnName = "id")
    private Gearbox gearbox;

    @OneToMany(mappedBy = "transmission", cascade = CascadeType.ALL)
    private List<Equipment>equipments = new ArrayList<>();

    public void removeEquipments(List<Equipment> equipments) {
        this.equipments.removeAll(equipments);
        equipments.forEach(equipment -> equipment.setTransmission(null));
    }
}
