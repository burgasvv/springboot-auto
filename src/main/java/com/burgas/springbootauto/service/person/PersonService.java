package com.burgas.springbootauto.service.person;

import com.burgas.springbootauto.entity.image.Image;
import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.repository.person.PersonRepository;
import com.burgas.springbootauto.repository.person.RoleRepository;
import com.burgas.springbootauto.service.image.ImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Page<Person> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        pageRequest.withSort(Sort.by(Sort.Direction.DESC, "username","firstname","lastname"));
        return personRepository.findAll(pageRequest);
    }

    public Page<Person> searchAllByFirstnameAndLastnameAndUsername(String search, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size)
                .withSort(Sort.by(Sort.Direction.DESC, "username","firstname","lastname"));
        return personRepository.searchAllByFirstnameAndLastnameAndUsername(search, pageRequest);
    }

    public Person findById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    public Person findPersonByUsername(String name) {
        return personRepository.findPersonByUsername(name);
    }

    @SneakyThrows
    @Transactional
    public void createUser(Person person, MultipartFile multipartFile) {
        if (personRepository.findPersonByUsername(person.getUsername()) != null)
            return;
        person.setEnabled(true);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole(roleRepository.findByName("USER"));
        if (multipartFile.getSize() != 0) {
            Image image = new Image();
            image.setPreview(true);
            image.setName(multipartFile.getName());
            image.setData(multipartFile.getBytes());
            person.addImage(image);
        }
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
