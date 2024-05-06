package com.burgas.springbootauto.repository;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.car.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> searchTagsByCars(List<Car> cars);
}
