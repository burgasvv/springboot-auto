package com.burgas.springbootauto.repository.image;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.image.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findByName(String name);

    Page<Image>findImagesByCarId(Long carId, Pageable pageable);
}
