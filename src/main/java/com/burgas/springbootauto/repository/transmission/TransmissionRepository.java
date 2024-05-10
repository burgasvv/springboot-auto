package com.burgas.springbootauto.repository.transmission;

import com.burgas.springbootauto.entity.transmission.Transmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransmissionRepository extends JpaRepository<Transmission, Long> {
}
