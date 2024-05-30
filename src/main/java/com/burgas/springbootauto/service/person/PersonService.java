package com.burgas.springbootauto.service.person;

import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.repository.person.PersonRepository;
import com.burgas.springbootauto.repository.person.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public List<Person> searchAllByFirstnameAndLastnameAndUsername(String search) {
        return personRepository.searchAllByFirstnameAndLastnameAndUsername(search);
    }

    public Person findById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public Person findPersonByUsername(String name) {
        return personRepository.findPersonByUsername(name);
    }

    @Transactional
    public void createUser(Person person) {
        if (personRepository.findPersonByUsername(person.getUsername()) != null)
            return;
        person.setEnabled(true);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole(roleRepository.findByName("USER"));
        personRepository.save(person);
    }

    @Transactional
    public void update(Person person) {
        person.setEnabled(true);
        Person oldPerson = personRepository.findById(person.getId()).orElse(null);
        if (!person.getPassword().equals(Objects.requireNonNull(oldPerson).getPassword())) {
            person.setPassword(passwordEncoder.encode(person.getPassword()));
        }
        person.setRole(roleRepository.findByName("USER"));
        personRepository.save(person);
    }

    @Transactional
    public void delete(Person person) {
        personRepository.deleteById(person.getId());
    }

    @Transactional
    public Person makeAdmin(Person person) {
        person.setRole(roleRepository.findByName("ADMIN"));
        return personRepository.save(person);
    }

    @Transactional
    public Person ban(Person person) {
        person.setEnabled(false);
        return personRepository.save(person);
    }

    @Transactional
    public Person unban(Person person) {
        person.setEnabled(true);
        return personRepository.save(person);
    }
}
