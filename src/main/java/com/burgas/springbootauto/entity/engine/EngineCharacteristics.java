package com.burgas.springbootauto.entity.engine;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
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
    @JoinColumn(name = "engine_id", referencedColumnName = "id")
    private Engine engine;
}
