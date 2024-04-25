package com.burgas.springbootauto.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Getter
@Setter
@Component
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    private int id;

    @NotEmpty(message = "This field can't be empty")
    private String title;
    private int brandId;
    private int classId;
    private int categoryId;
    private String image;
    private String webpage;
    private String description;
    private int price;
}
