package com.burgas.springbootauto.repository;

import com.burgas.springbootauto.entity.engine.Engine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EngineRepository extends JpaRepository<Engine, Long> {

    List<Engine> searchEnginesByEngineEditionId(Long id);
}
