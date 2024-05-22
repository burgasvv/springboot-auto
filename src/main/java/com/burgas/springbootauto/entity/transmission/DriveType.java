package com.burgas.springbootauto.entity.transmission;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class DriveType {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "driveType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Transmission>transmissions = new ArrayList<>();
}
