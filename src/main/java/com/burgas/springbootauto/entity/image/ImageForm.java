package com.burgas.springbootauto.entity.image;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageForm {

    private Long id;
    private String name;
    private boolean isPreview;
}
