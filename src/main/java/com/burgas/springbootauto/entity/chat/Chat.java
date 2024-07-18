package com.burgas.springbootauto.entity.chat;

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
public class Chat {

    @Id
    @GeneratedValue
    private Long id;

    @SuppressWarnings("JpaDataSourceORMInspection")
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private List<Person> people = new ArrayList<>();

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Message> messages = new ArrayList<>();
}
