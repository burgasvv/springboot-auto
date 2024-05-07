package com.burgas.springbootauto.entity.engine;

import com.burgas.springbootauto.entity.brand.Brand;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class EngineEdition {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @OneToMany(mappedBy = "engineEdition", cascade = CascadeType.ALL)
    private List<Engine>engines;
}