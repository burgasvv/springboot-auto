package com.burgas.springbootauto.entity.transmission;

import com.burgas.springbootauto.entity.brand.Brand;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Gearbox {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Check(constraints = "stairs > 0 and stairs < 10")
    private Integer stairs;

    @Column(nullable = false)
    private String image;

    @ManyToMany(mappedBy = "gearboxes", cascade = CascadeType.ALL)
    private List<Brand> brands = new ArrayList<>();

    @OneToMany(mappedBy = "gearbox", cascade = CascadeType.ALL)
    private List<Transmission>transmissions = new ArrayList<>();
}
