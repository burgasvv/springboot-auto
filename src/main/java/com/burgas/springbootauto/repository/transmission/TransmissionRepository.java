package com.burgas.springbootauto.repository.transmission;

import com.burgas.springbootauto.entity.transmission.Transmission;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransmissionRepository extends JpaRepository<Transmission, Long> {

    @Modifying
    @Query(
            nativeQuery = true,
            value = "delete from transmission where id = ?1"
    )
    void deleteById(@NotNull Long id);

    Transmission findTransmissionByName(String name);
}
