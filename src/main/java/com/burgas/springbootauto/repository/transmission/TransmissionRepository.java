package com.burgas.springbootauto.repository.transmission;

import com.burgas.springbootauto.entity.transmission.Transmission;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransmissionRepository extends JpaRepository<Transmission, Long> {

    @Modifying
    @Query(
            nativeQuery = true,
            value = "delete from transmission where id = ?1"
    )
    void deleteById(@NotNull Long id);

    Transmission findTransmissionByName(String name);

    @Query(
            nativeQuery = true,
            value = """
                    select tr.* from transmission tr
                    join brand b on b.id = tr.brand_id
                    join gearbox g on g.id = tr.gearbox_id
                    join drive_type dt on dt.id = tr.drivetype_id
                    join equipment e on tr.id = e.transmission_id
                    join car c on c.id = e.car_id
                    where concat(b.title,' ',c.title,' ',tr.name,' ',c.title,' ',b.title,' ',c.title,' ',g.name,' ',b.title,' ',c.title,' ',
                    dt.name,' ',c.title,' ',b.title,' ',g.name,' ',tr.name,' ',dt.name,' ',c.title,' ',g.name,' ',dt.name,' ',
                    tr.name,' ',c.title,' ',g.name,' ',dt.name,' ',tr.name,' ') ilike concat('%',?1,'%')"""
    )
    List<Transmission> searchTransmissionsByNeighbourNames(String search);

    @Query(
            nativeQuery = true,
            value = """
                    select tr.* from transmission tr
                    join brand b on b.id = tr.brand_id
                    join gearbox g on g.id = tr.gearbox_id
                    join drive_type dt on dt.id = tr.drivetype_id
                    where concat(b.title,tr.name,b.title,g.name,b.title,dt.name,b.title,g.name,
                    tr.name,dt.name,g.name,dt.name,tr.name,g.name,dt.name,tr.name) ilike concat('%',?1,'%')"""
    )
    List<Transmission> searchTransmissionsByNeighbourNamesNoSpaces(String search);
}
