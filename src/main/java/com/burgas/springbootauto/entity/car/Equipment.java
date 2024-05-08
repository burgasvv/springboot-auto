package com.burgas.springbootauto.entity.car;

import com.burgas.springbootauto.entity.engine.Engine;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Equipment {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    @OneToOne(mappedBy = "equipment", cascade = CascadeType.ALL)
    private Engine engine;

    public void addEngine(Engine engine) {
        this.engine = engine;
        engine.setEquipment(this);
    }

    public void removeEngine(Engine engine) {
        this.engine = null;
        engine.setEquipment(null);
    }
}
