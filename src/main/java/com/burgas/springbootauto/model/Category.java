package com.burgas.springbootauto.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    private int id;
    private String name;
    private String image;
    private String description;
}
