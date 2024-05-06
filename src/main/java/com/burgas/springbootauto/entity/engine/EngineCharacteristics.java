package com.burgas.springbootauto.entity.engine;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Check;

@Data
@Entity
public class EngineCharacteristics {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    @Check(constraints = "cylinders >= 0")
    private Integer cylinders;

    @Column
    private String volume;

    @Column
    private String piston;

    @Column
    @Check(constraints = "compression >= 0")
    private Double compression;

    @Column
    private String power;

    @Column
    private String torque;

    @OneToOne
    @JoinColumn(name = "engine_id", referencedColumnName = "id")
    private Engine engine;
}
