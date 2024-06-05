package com.burgas.springbootauto.entity.image;

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

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false)
    private byte[] data;

    @Column(nullable = false)
    private boolean isPreview;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;
}
