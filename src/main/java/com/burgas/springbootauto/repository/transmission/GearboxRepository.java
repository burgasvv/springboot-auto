package com.burgas.springbootauto.repository.transmission;

import com.burgas.springbootauto.entity.transmission.Gearbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GearboxRepository extends JpaRepository<Gearbox, Long> {
}
