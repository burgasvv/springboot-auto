package com.burgas.springbootauto.entity.turbocharging;

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
public class TurboType {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "turboType", cascade = CascadeType.ALL)
    private List<Turbocharger>turbochargers = new ArrayList<>();
}
