package com.burgas.springbootauto.entity.communication.cheer;

import com.burgas.springbootauto.entity.news.News;
import com.burgas.springbootauto.entity.person.Person;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cheer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long amount;

    @OneToOne
    @JoinColumn(name = "news_id")
    private News news;

    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "cheer_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private List<Person>people = new ArrayList<>();
}
