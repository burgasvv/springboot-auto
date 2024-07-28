package com.burgas.springbootauto.entity.news;

import com.burgas.springbootauto.entity.communication.cheer.Cheer;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @GeneratedValue
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String url;

    @Column(columnDefinition = "TEXT")
    private String image;

    @OneToOne(mappedBy = "news", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Cheer cheer;

    @Column
    private String date;
}
