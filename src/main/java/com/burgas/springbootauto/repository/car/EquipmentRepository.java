package com.burgas.springbootauto.repository.car;

import com.burgas.springbootauto.entity.car.Equipment;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    @NotNull
    Page<Equipment>findAll(@NotNull Pageable pageable);

    Page<Equipment>findAllByPersonId(Long id, Pageable pageable);

    List<Equipment> findAllByCarId(@NotNull Long carId);

    List<Equipment> findAllByEngineId(Long id);

    List<Equipment> findAllByTransmissionId(Long id);

    List<Equipment> findAllByTurbochargerId(Long id);

    @Modifying
    @Query(
            nativeQuery = true,
            value = "delete from equipment where id = ?1"
    )
    void deleteById(@NotNull Long id);

    @Query(
            nativeQuery = true,
            value = """
                select distinct e.* from equipment e
                join car c on c.id = e.car_id
                join brand b on b.id = c.brand_id
                join person p on p.id = c.person_id
                where concat(b.title,' ',c.title,' ',e.name,' ',p.username,' ',p.firstname,' ',p.lastname,' ',e.name,' ',c.title,' ',
                      b.title,' ',e.name,' ',b.title,' ',p.username,' ',p.lastname,' ',e.name,' ',p.firstname,' ',b.title,' ',p.lastname,' ',
                      c.title,' ',p.username,' ',b.title,' ',p.firstname,' ',p.username,' ',p.lastname,' ',b.title) ilike concat('%',?1,'%')
                """
    )
    Page<Equipment> searchEquipmentsByCarAndPerson(String search, Pageable pageable);

    @Query(
            nativeQuery = true,
            value = """
                select distinct e.* from equipment e
                join car c on c.id = e.car_id
                join brand b on b.id = c.brand_id
                join person p on p.id = c.person_id
                where p.username = ?1 and
                concat(e.name,' ',c.title,' ',b.title,' ',c.title,' ',e.name,' ',b.title,' ') ilike concat('%',?2,'%')
                """
    )
    Page<Equipment> searchEquipmentsByBrandAndCar(String username, String search, Pageable pageable);
}
