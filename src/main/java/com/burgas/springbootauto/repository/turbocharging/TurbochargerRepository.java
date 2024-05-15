package com.burgas.springbootauto.repository.turbocharging;

import com.burgas.springbootauto.entity.turbocharging.Turbocharger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurbochargerRepository extends JpaRepository<Turbocharger, Long> {
}
