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
    private List<Person> peopleLikesNews = new ArrayList<>();

    public void personLikedNews(Person person) {
        this.peopleLikesNews.add(person);
        person.getNewsCheers().add(this);
    }

    public void personDislikedNews(Person person) {
        this.peopleLikesNews.removeIf(p -> p.getId().equals(person.getId()));
        person.getNewsCheers().remove(this);
    }
}
