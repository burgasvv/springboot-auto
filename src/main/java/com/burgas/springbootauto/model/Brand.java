package com.burgas.springbootauto.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class Brand {

    private int id;

    @NotEmpty(message = "This field can't be empty")
    private String title;
    private String image;
    private String website;
    private String description;
}
