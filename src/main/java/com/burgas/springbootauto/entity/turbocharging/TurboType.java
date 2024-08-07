package com.burgas.springbootauto.entity.turbocharging;

import com.burgas.springbootauto.entity.brand.Brand;
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
public class TurboType {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "turboTypes", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Brand>brands = new ArrayList<>();

    public void addBrand(Brand brand) {
        brands.add(brand);
        brand.getTurboTypes().add(this);
    }

    @OneToMany(mappedBy = "turboType", cascade = CascadeType.ALL)
    private List<Turbocharger>turbochargers = new ArrayList<>();
}
