package com.burgas.springbootauto.entity.communication.cheer;

import com.burgas.springbootauto.entity.news.News;
import com.burgas.springbootauto.entity.person.Person;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
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
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "cheer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id")
    )
    private List<Person> people = new ArrayList<>();

    public void addPerson(Person person) {
        this.people.add(person);
        person.getCheers().add(this);
    }

    public void removePerson(Person person) {
        this.people.removeIf(p -> p.getId().equals(person.getId()));
        person.getCheers().remove(this);
    }
}
