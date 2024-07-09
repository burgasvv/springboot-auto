package com.burgas.springbootauto.entity.brand;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.engine.EngineEdition;
import com.burgas.springbootauto.entity.image.Image;
import com.burgas.springbootauto.entity.transmission.Gearbox;
import com.burgas.springbootauto.entity.transmission.Transmission;
import com.burgas.springbootauto.entity.turbocharging.TurboType;
import com.burgas.springbootauto.entity.turbocharging.Turbocharger;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Brand {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(columnDefinition = "TEXT")
    private String website;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EngineEdition>engineEditions = new ArrayList<>();

    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "brand_id"),
            inverseJoinColumns = @JoinColumn(name = "gearbox_id")
    )
    private List<Gearbox>gearboxes = new ArrayList<>();

    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "brand_id"),
            inverseJoinColumns = @JoinColumn(name = "turbotype_id")
    )
    private List<TurboType>turboTypes = new ArrayList<>();

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Turbocharger>turbochargers = new ArrayList<>();

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transmission>transmissions = new ArrayList<>();

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH}
    )
    private List<Car>cars = new ArrayList<>();
}
