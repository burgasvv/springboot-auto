package com.burgas.springbootauto.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Getter
@Setter
@Component
@NoArgsConstructor
@AllArgsConstructor
public class Class {

    private int id;
    private String name;
    private String image;
    private String description;
}
