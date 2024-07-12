package com.burgas.springbootauto.repository.person;

import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.entity.person.Status;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @NotNull
    @Query(
            nativeQuery = true,
            value = """
                    select p.* from person p where p.verified = true"""
    )
    Page<Person> findAll(@NotNull Pageable pageable);

    Person findPersonByUsername(String name);

    Person findPersonByEmail(String email);

    @Query(
            nativeQuery = true,
            value = """
                    select p.* from person p
                               where p.verified = true and concat(p.firstname,' ',p.lastname,' ',p.username,' ',p.username,' ',\
                    p.firstname,' ',p.username,' ',p.lastname,' ',p.firstname,' ')
                                         ilike concat('%',?1,'%')"""
    )
    Page<Person> searchAllByFirstnameAndLastnameAndUsername(String search, Pageable pageable);

    void deleteById(@NotNull Long id);

    List<Person> findPeopleByStatus(Status status);
}
