package com.burgas.springbootauto.entity.car;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL)
    private List<Car>cars = new ArrayList<>();
}
