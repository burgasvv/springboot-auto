package com.burgas.springbootauto.repository.engine;

import com.burgas.springbootauto.entity.engine.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelRepository extends JpaRepository<Fuel, Long> {
}
