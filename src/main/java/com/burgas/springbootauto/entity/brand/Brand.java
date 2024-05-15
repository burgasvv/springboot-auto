package com.burgas.springbootauto.entity.brand;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.engine.EngineEdition;
import com.burgas.springbootauto.entity.transmission.Gearbox;
import com.burgas.springbootauto.entity.transmission.Transmission;
import com.burgas.springbootauto.entity.turbocharging.TurboType;
import com.burgas.springbootauto.entity.turbocharging.Turbocharger;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Brand {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String image;

    @Column(nullable = false)
    private String website;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<EngineEdition>engineEditions = new ArrayList<>();

    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "brand_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "gearbox_id", referencedColumnName = "id")
    )
    private List<Gearbox>gearboxes = new ArrayList<>();

    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "brand_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "turbotype_id", referencedColumnName = "id")
    )
    private List<TurboType>turboTypes = new ArrayList<>();

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Turbocharger>turbochargers = new ArrayList<>();

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Transmission>transmissions = new ArrayList<>();

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Car>cars = new ArrayList<>();
}
