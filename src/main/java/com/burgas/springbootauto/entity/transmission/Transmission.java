package com.burgas.springbootauto.entity.transmission;

import com.burgas.springbootauto.entity.brand.Brand;
import com.burgas.springbootauto.entity.car.Equipment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transmission {

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
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "drivetype_id", referencedColumnName = "id")
    private DriveType driveType;

    @ManyToOne
    @JoinColumn(name = "gearbox_id", referencedColumnName = "id")
    private Gearbox gearbox;

    @OneToMany(mappedBy = "transmission", cascade = CascadeType.ALL)
    private List<Equipment>equipments = new ArrayList<>();
}
