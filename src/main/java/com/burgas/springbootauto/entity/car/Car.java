package com.burgas.springbootauto.entity.car;

import com.burgas.springbootauto.entity.brand.Brand;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Car {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String image;

    @Column(nullable = false)
    private String webpage;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    @Check(constraints = "price >= 0")
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "classification_id", referencedColumnName = "id")
    private Classification classification;

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL)
    private Equipment equipment;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private List<Tag>tags = new ArrayList<>();
}
