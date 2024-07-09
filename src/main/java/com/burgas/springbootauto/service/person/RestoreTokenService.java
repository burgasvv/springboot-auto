package com.burgas.springbootauto.service.person;

import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.entity.person.RestoreToken;
import com.burgas.springbootauto.repository.person.RestoreTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestoreTokenService {

    private final RestoreTokenRepository restoreTokenRepository;

    public RestoreToken findByToken(String token) {
        return restoreTokenRepository.findByToken(token);
    }

    public RestoreToken findTokenByPerson(Person person) {
        return restoreTokenRepository.findByPerson(person);
    }

    public boolean existsRestoreTokenByPerson(Person person) {
        return restoreTokenRepository.existsRestoreTokenByPerson(person);
    }

    @Transactional
    public RestoreToken save(final RestoreToken restoreToken) {
        return restoreTokenRepository.save(restoreToken);
    }

    @Transactional
    public void delete(final RestoreToken restoreToken) {
        restoreTokenRepository.deleteById(restoreToken.getId());
    }
}
