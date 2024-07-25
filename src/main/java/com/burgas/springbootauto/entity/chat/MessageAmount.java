package com.burgas.springbootauto.entity.chat;

import com.burgas.springbootauto.entity.person.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageAmount {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long amount;

    @SuppressWarnings("JpaDataSourceORMInspection")
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "receiver_id")
    private Person receiver;
}
