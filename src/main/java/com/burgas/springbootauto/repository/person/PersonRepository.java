package com.burgas.springbootauto.repository.person;

import com.burgas.springbootauto.entity.car.Equipment;
import com.burgas.springbootauto.entity.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findPersonByUsername(String name);

    Person findPersonByEquipments(List<Equipment>equipments);
}
