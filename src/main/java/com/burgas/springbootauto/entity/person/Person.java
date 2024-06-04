package com.burgas.springbootauto.entity.person;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.car.Equipment;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
public class Person implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "TEXT")
    private String image;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Car>cars = new ArrayList<>();

    public void addCar(Car car) {
        cars.add(car);
        car.setPerson(this);
    }

    public void removeCar(Car car) {
        cars.remove(car);
        car.setPerson(null);
    }

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Equipment>equipments = new ArrayList<>();

    public void addEquipment(Equipment equipment) {
        equipments.add(equipment);
        equipment.setPerson(this);
    }

    public void removeEquipment(Equipment equipment) {
        equipments.remove(equipment);
        equipment.setPerson(null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getName()));
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
