package com.burgas.springbootauto.entity.engine;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EngineCharacteristics {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String cylinders;

    @Column
    private String volume;

    @Column
    private String piston;

    @Column
    private String compression;

    @Column
    private String power;

    @Column
    private String torque;

    @SuppressWarnings("JpaDataSourceORMInspection")
    @OneToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;
}
