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

    @Query(
            nativeQuery = true,
            value = """
                    select en.* from engine en
                    join fuel f on f.id = en.fuel_id
                    join engine_edition ee on ee.id = en.edition_id
                    join brand b on b.id = ee.brand_id
                    join car c on b.id = c.brand_id
                    where concat(b.title,' ',en.name,' ',b.title,' ',ee.name,' ',b.title,' ',f.name,' ',b.title,' ',c.title,' ',f.name,' ',
                          c.title,' ',b.title,' ',ee.name,' ',en.name,' ',ee.name,' ',c.title,' ',ee.name,' ',f.name,' ',ee.name,' ',c.title,' ',
                          en.name,' ',ee.name,' ') ilike concat('%',?1,'%')"""
    )
    List<Engine> searchEnginesByEngineBrandEditionCar(String search);

    @Query(
            nativeQuery = true,
            value = """
                    select en.* from engine en
                    join fuel f on f.id = en.fuel_id
                    join engine_edition ee on ee.id = en.edition_id
                    join brand b on b.id = ee.brand_id
                    join public.car c on b.id = c.brand_id
                    where concat(b.title,en.name,b.title,ee.name,b.title,f.name,b.title,c.title,f.name,
                          c.title,b.title,ee.name,en.name,ee.name,c.title,ee.name,f.name,ee.name,c.title,
                          en.name,ee.name) ilike concat('%',?1,'%')"""
    )
    List<Engine> searchEnginesByEngineBrandEditionCarNoSpaces(String search);

    Engine findEngineByName(String engineName);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "delete from engine e where e.id = ?1"
    )
    void deleteById(@NotNull Long id);
}
