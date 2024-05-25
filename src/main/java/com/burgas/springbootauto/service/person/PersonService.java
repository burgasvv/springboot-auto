package com.burgas.springbootauto.service.person;

import com.burgas.springbootauto.entity.car.Equipment;
import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.entity.person.Role;
import com.burgas.springbootauto.repository.person.PersonRepository;
import com.burgas.springbootauto.repository.person.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public Person findPersonByUsername(String name) {
        return personRepository.findPersonByUsername(name);
    }

    public Person findPersonByEquipments(List<Equipment> equipments) {
        return personRepository.findPersonByEquipments(equipments);
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
}
