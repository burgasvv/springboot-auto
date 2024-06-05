package com.burgas.springbootauto.repository.image;

import com.burgas.springbootauto.entity.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findByName(String name);
}
