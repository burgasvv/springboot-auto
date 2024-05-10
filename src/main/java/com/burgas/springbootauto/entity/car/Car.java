package com.burgas.springbootauto.entity.car;

import com.burgas.springbootauto.entity.brand.Brand;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    public void addBrand(Brand brand) {
        this.brand = brand;
        this.brand.getCars().add(this);
    }

    public void removeBrand(Brand brand) {
        this.brand = null;
        brand.getCars().remove(this);
    }

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "classification_id", referencedColumnName = "id")
    private Classification classification;

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL)
    private Equipment equipment;

    public void addEquipment(Equipment equipment) {
        this.equipment = equipment;
        this.equipment.setCar(this);
    }

    public void removeEquipment(Equipment equipment) {
        this.equipment = null;
        equipment.setCar(null);
    }

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private List<Tag>tags = new ArrayList<>();

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getCars().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getCars().remove(this);
    }

    public void addTags(List<Tag> tags) {
        this.tags.addAll(tags);
        tags.forEach(tag -> tag.getCars().add(this));
    }

    public void removeTags(List<Tag> tags) {
        this.tags.removeAll(tags);
        tags.forEach(tag -> tag.getCars().remove(this));
    }
}
