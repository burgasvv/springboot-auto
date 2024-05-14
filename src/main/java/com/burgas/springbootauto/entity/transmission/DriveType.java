package com.burgas.springbootauto.entity.transmission;

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
public class DriveType {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "driveType", cascade = CascadeType.ALL)
    private List<Transmission>transmissions = new ArrayList<>();
}
