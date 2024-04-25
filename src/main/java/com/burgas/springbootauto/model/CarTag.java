package com.burgas.springbootauto.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Getter
@Setter
@Component
@NoArgsConstructor
@AllArgsConstructor
public class CarTag {

    private int carId;
    private int tagId;
}
