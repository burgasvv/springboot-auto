package com.burgas.springbootauto.repository.car;

import com.burgas.springbootauto.entity.car.DriveUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriveUnitRepository extends JpaRepository<DriveUnit, Long> {
}
