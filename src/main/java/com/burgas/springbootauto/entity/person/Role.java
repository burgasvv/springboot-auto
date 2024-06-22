package com.burgas.springbootauto.entity.person;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<Person> persons = new ArrayList<>();

    @Override
    public String getAuthority() {
        return name;
    }
}
