package com.burgas.springbootauto.service.person;

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

    @Transactional
    public RestoreToken save(final RestoreToken restoreToken) {
        return restoreTokenRepository.save(restoreToken);
    }
}
