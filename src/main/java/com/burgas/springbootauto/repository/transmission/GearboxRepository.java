package com.burgas.springbootauto.repository.transmission;

import com.burgas.springbootauto.entity.transmission.Gearbox;
import com.burgas.springbootauto.entity.transmission.Transmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GearboxRepository extends JpaRepository<Gearbox, Long> {

    Gearbox searchGearboxByTransmissions(List<Transmission> transmissions);
}
