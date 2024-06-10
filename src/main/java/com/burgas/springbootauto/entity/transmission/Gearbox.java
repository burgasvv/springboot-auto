package com.burgas.springbootauto.entity.transmission;

import com.burgas.springbootauto.entity.brand.Brand;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Gearbox {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    @Check(constraints = "stairs > 0 and stairs < 10")
    private Integer stairs;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String image;

    @ManyToMany(mappedBy = "gearboxes", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Brand> brands = new ArrayList<>();

    public void addBrand(Brand brand) {
        brands.add(brand);
        brand.getGearboxes().add(this);
    }

    @OneToMany(mappedBy = "gearbox", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transmission>transmissions = new ArrayList<>();
}
