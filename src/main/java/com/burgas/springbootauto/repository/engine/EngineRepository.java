package com.burgas.springbootauto.repository.engine;

import com.burgas.springbootauto.entity.engine.Engine;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EngineRepository extends JpaRepository<Engine, Long> {

    List<Engine> searchEnginesByEngineEditionId(Long id);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "delete from engine e where e.id = ?1"
    )
    void deleteById(@NotNull Long id);
}
