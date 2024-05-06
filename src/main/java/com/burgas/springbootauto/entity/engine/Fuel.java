package com.burgas.springbootauto.entity.engine;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Fuel {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "fuel", cascade = CascadeType.ALL)
    private List<Engine>engines = new ArrayList<>();
}
