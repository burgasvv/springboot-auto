package com.burgas.springbootauto.repository.person;

import com.burgas.springbootauto.entity.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findPersonByUsername(String name);
}
