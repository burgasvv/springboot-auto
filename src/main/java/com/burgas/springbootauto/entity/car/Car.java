package com.burgas.springbootauto.entity.car;

import com.burgas.springbootauto.entity.brand.Brand;
import com.burgas.springbootauto.entity.image.Image;
import com.burgas.springbootauto.entity.person.Person;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Car {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();

    public void addImage(Image image) {
        this.images.add(image);
        image.setCar(this);
    }

    public void addImages(List<Image> images) {
        this.images.addAll(images);
        images.forEach(this::addImage);
    }

    public void removeImage(Image image) {
        this.images.remove(image);
        image.setCar(null);
    }

    public void removeImages(List<Image> images) {
        this.images.removeAll(images);
        images.forEach(this::removeImage);
    }

    @Column(nullable = false)
    private boolean hasPreview;

    @Column(nullable = false)
    private String webpage;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "drive_unit_id")
    private DriveUnit driveUnit;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "classification_id")
    private Classification classification;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Equipment> equipments = new ArrayList<>();

    public void addEquipment(Equipment equipment) {
        equipment.setAttached(true);
        equipment.setCar(this);
        equipments.add(equipment);
    }

    public void removeEquipment(Equipment equipment) {
        equipment.setAttached(false);
        equipment.setCar(null);
        equipments.remove(equipment);
    }

    public void addEquipments(List<Equipment> equipments) {
        equipments.forEach(equipment -> equipment.setAttached(true));
        equipments.forEach(equipment -> equipment.setCar(this));
        this.equipments.addAll(equipments);
    }

    public void removeEquipments(List<Equipment> equipments) {
        equipments.forEach(equipment -> equipment.setAttached(false));
        equipments.forEach(equipment -> equipment.setCar(null));
        this.equipments.removeAll(equipments);
    }

    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag>tags = new ArrayList<>();
}
