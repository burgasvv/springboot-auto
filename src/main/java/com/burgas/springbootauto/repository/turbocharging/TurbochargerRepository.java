package com.burgas.springbootauto.repository.turbocharging;

import com.burgas.springbootauto.entity.turbocharging.Turbocharger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurbochargerRepository extends JpaRepository<Turbocharger, Long> {

    Turbocharger findByName(String name);

    @Query(
            nativeQuery = true,
            value = """
                    select tu.* from turbocharger tu
                    join brand b on b.id = tu.brand_id
                    join turbo_type tt on tt.id = tu.turbotype_id
                    join equipment e on tu.id = e.turbocharger_id
                    join car c on c.id = e.car_id
                    where concat(b.title,' ',tt.name,' ',tu.name,' ',b.title,' ',tu.name,' ',tt.name,' ',b.title,' ',c.title,' ',
                          b.title,' ',c.title,' ',tt.name,' ',tu.name,' ',c.title,' ',tu.name,' ',tt.name,' ',c.title,' ')
                    ilike concat('%',?1,'%')"""
    )
    List<Turbocharger>searchTurbochargersByNeighbourNames(String search);

    @Query(
            nativeQuery = true,
            value = """
                    select tu.* from turbocharger tu
                    join public.brand b on b.id = tu.brand_id
                    join public.turbo_type tt on tt.id = tu.turbotype_id
                    where concat(b.title,tt.name,tu.name,b.title,tu.name,tt.name,b.title)
                    ilike concat('%',?1,'%')"""
    )
    List<Turbocharger>searchTurbochargersByNeighbourNamesNoSpaces(String search);
}
