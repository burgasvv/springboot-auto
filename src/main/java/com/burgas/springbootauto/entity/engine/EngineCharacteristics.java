package com.burgas.springbootauto.entity.engine;

import com.burgas.springbootauto.calculations.EquipmentDataProcessing;
import jakarta.persistence.*;
import lombok.*;

import java.text.NumberFormat;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EngineCharacteristics {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String cylinders;

    @Column(nullable = false)
    private String volume;

    @Column(nullable = false)
    private String  rpm;

    @Column(nullable = false)
    private String torque;

    @SuppressWarnings("JpaDataSourceORMInspection")
    @OneToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;

    public String getPower() {
        EquipmentDataProcessing edp = new EquipmentDataProcessing();
        double power = edp.getDouble(torque) * edp.getDouble(rpm) / 9549.0 * 1.36;
        NumberFormat instance = NumberFormat.getInstance();
        instance.setMinimumFractionDigits(2);
        return instance.format(power);
    }
}
