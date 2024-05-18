package com.burgas.springbootauto.repository.car;

import com.burgas.springbootauto.entity.car.Classification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificationRepository extends JpaRepository<Classification, Long> {

    Classification findClassificationByName(String name);
}
