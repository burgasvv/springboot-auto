package com.burgas.springbootauto.service.person;

import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.entity.person.RestoreToken;
import com.burgas.springbootauto.repository.person.RestoreTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public RestoreToken save(final RestoreToken restoreToken) {
        return restoreTokenRepository.save(restoreToken);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void delete(final RestoreToken restoreToken) {
        restoreTokenRepository.deleteById(restoreToken.getId());
    }
}
