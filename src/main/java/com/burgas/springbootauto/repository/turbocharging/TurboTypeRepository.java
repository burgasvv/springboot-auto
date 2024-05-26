package com.burgas.springbootauto.repository.turbocharging;

import com.burgas.springbootauto.entity.turbocharging.TurboType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurboTypeRepository extends JpaRepository<TurboType, Long> {

    TurboType findByName(String name);
}
