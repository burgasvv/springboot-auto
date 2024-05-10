package com.burgas.springbootauto.repository.car;

import com.burgas.springbootauto.entity.car.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
