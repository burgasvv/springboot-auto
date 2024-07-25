package com.burgas.springbootauto.service.person;

import com.burgas.springbootauto.entity.chat.MessageAmount;
import com.burgas.springbootauto.entity.image.Image;
import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.entity.person.Status;
import com.burgas.springbootauto.repository.person.PersonRepository;
import com.burgas.springbootauto.repository.person.RoleRepository;
import com.burgas.springbootauto.service.chat.MessageService;
import com.burgas.springbootauto.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
public class PersonService {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;
    private final MessageService messageService;

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

    public Person findPersonByEmail(String email) {
        return personRepository.findPersonByEmail(email);
    }

    @SneakyThrows
    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
    public Person createUser(Person person, MultipartFile multipartFile) {
        if (personRepository.findPersonByUsername(person.getUsername()) != null)
            return null;
        person.setStatus(Status.OFFLINE);
        person.setEnabled(false);
        person.setVerified(false);
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole(roleRepository.findByName("USER"));
        if (multipartFile.getSize() != 0) {
            Image image = new Image();
            image.setPreview(true);
            image.setName(multipartFile.getOriginalFilename());
            image.setData(multipartFile.getBytes());
            imageService.save(image);
            person.setImage(image);
        }
        return personRepository.save(person);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void connectUser(Person person) {
        person.setStatus(Status.ONLINE);
        personRepository.save(person);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void disconnectUser(Person person) {
        person.setStatus(Status.OFFLINE);
        personRepository.save(person);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void restorePassword(Person person, String password) {
        person.setPassword(passwordEncoder.encode(password));
        personRepository.save(person);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.NESTED)
    public void update(Person person) {
        person.setEnabled(true);
        person.setStatus(Status.OFFLINE);
        Person oldPerson = personRepository.findById(person.getId()).orElse(null);
        if (!person.getPassword().equals(Objects.requireNonNull(oldPerson).getPassword())) {
            person.setPassword(passwordEncoder.encode(person.getPassword()));
        }
        personRepository.save(person);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
    public void delete(String name) {
        Person person = personRepository.findPersonByUsername(name);
        person.getChats().forEach(chat -> chat.getMessages().forEach(message -> {
            if (message.getSender().equals(person)) {
                message.setSender(null);
                messageService.save(message);
            }
            if (message.getReceiver().equals(person)) {
                message.setReceiver(null);
                messageService.save(message);
            }
        }));
        person.getChats().forEach(chat -> chat.getPeople().remove(person));
        personRepository.save(person);
        personRepository.deleteById(person.getId());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Person makeAdmin(Person person) {
        person.setRole(roleRepository.findByName("ADMIN"));
        return personRepository.save(person);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Person ban(String name) {
        Person person = personRepository.findPersonByUsername(name);
        person.setEnabled(false);
        return personRepository.save(person);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Person unban(String name) {
        Person person = personRepository.findPersonByUsername(name);
        person.setEnabled(true);
        return personRepository.save(person);
    }

    @SneakyThrows
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void changeImage(Person person, MultipartFile file) {
        if (imageService.findByName(file.getOriginalFilename()) != null) {
            return;
        }
        if (file.getSize() != 0) {
            Image image = new Image();
            image.setPreview(true);
            image.setName(file.getOriginalFilename());
            image.setData(file.getBytes());
            imageService.save(image);
            person.setImage(image);
        }
        personRepository.save(person);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void removeImage(Person person) {
        Image image = person.getImage();
        person.setImage(null);
        personRepository.save(person);
        imageService.delete(image);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void activateAccount(Person person) {
        person.setEnabled(true);
        person.setVerified(true);
        personRepository.save(person);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void plusMessage(Person person) {
        MessageAmount messageAmount = person.getMessageAmount();
        if (messageAmount == null) {
            person.setMessageAmount(
                    MessageAmount.builder().receiver(person).amount(1L).build()
            );
            personRepository.save(person);
        } else {
            messageAmount.setAmount(messageAmount.getAmount() + 1L);
            person.setMessageAmount(messageAmount);
            personRepository.save(person);
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void minusMessage(Person person) {
        MessageAmount messageAmount = person.getMessageAmount();
        messageAmount.setAmount(messageAmount.getAmount() - 1L);
        person.setMessageAmount(messageAmount);
        personRepository.save(person);
    }
}
