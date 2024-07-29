package com.burgas.springbootauto.repository.communication.cheer;

import com.burgas.springbootauto.entity.communication.cheer.Cheer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CheerRepository extends JpaRepository<Cheer, Long> {

    Optional<Cheer>findCheerByNewsId(Long newsId);
}
