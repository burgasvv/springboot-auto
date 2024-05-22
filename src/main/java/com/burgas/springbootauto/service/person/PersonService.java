package com.burgas.springbootauto.service.person;

import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.entity.person.Role;
import com.burgas.springbootauto.repository.person.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public Person findByName(String name) {
        return personRepository.findByName(name);
    }

    public void createUser(Person person) {
        if (personRepository.findByName(person.getName()) != null)
            return;
        person.setEnabled(true);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.getRoles().add(Role.USER);
        personRepository.save(person);
    }
}
