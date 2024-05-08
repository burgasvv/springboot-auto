package com.burgas.springbootauto.entity.engine;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Fuel {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "fuel", cascade = CascadeType.ALL)
    private List<Engine>engines = new ArrayList<>();

    public void addEngine(Engine engine) {
        engines.add(engine);
        engine.setFuel(this);
    }

    public void removeEngine(Engine engine) {
        engines.remove(engine);
        engine.setFuel(null);
    }
}
