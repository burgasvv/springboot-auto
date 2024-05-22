package com.burgas.springbootauto.entity.engine;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Fuel {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "fuel", cascade = CascadeType.ALL)
    private List<Engine>engines = new ArrayList<>();
}
