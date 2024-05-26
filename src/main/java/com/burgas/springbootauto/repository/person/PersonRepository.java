package com.burgas.springbootauto.repository.person;

import com.burgas.springbootauto.entity.car.Equipment;
import com.burgas.springbootauto.entity.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findPersonByUsername(String name);

    Person findPersonByEquipments(List<Equipment>equipments);

    @Query(
            nativeQuery = true,
            value = """
                    select p.* from person p
                               where concat(p.firstname,' ',p.lastname,' ',p.username,' ',p.username,' ',\
                    p.firstname,' ',p.username,' ',p.lastname,' ',p.firstname,' ')
                                         ilike concat('%',?1,'%')"""
    )
    List<Person> searchAllByFirstnameAndLastnameAndUsername(String search);
}
