package com.burgas.springbootauto.repository.person;

import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.entity.person.RestoreToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestoreTokenRepository extends JpaRepository<RestoreToken, Long> {

    RestoreToken findByToken(String token);

    RestoreToken findByPerson(Person person);
}
