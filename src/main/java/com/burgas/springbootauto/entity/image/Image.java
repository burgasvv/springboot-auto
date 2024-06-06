package com.burgas.springbootauto.entity.image;

import com.burgas.springbootauto.entity.brand.Brand;
import com.burgas.springbootauto.entity.person.Person;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Lob
    @Column(nullable = false)
    private byte[] data;

    @Column(nullable = false)
    private boolean isPreview;

    @OneToOne(mappedBy = "image", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Person person;

    @OneToOne(mappedBy = "image", cascade = CascadeType.ALL, orphanRemoval = true)
    private Brand brand;
}
