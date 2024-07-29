package com.burgas.springbootauto.service.communication.cheer;

import com.burgas.springbootauto.entity.communication.cheer.Cheer;
import com.burgas.springbootauto.entity.news.News;
import com.burgas.springbootauto.entity.person.Person;
import com.burgas.springbootauto.repository.communication.cheer.CheerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CheerService {

    private final CheerRepository cheerRepository;

    public List<Cheer> findAll() {
        return cheerRepository.findAll();
    }

    public Cheer findById(Long id) {
        return cheerRepository.findById(id).orElse(null);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE,propagation = Propagation.REQUIRED)
    public void newsCheerPlus(News article, Person person) {
        Cheer cheer = cheerRepository.findById(article.getId()).orElse(null);
        Objects.requireNonNull(cheer).setAmount(cheer.getAmount() + 1);
        cheer.addPerson(person);
        cheerRepository.save(cheer);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE,propagation = Propagation.REQUIRED)
    public void newsCheerMinus(News article, Person person) {
        Cheer cheer = cheerRepository.findCheerByNewsId(article.getId()).orElse(null);
        Objects.requireNonNull(cheer).setAmount(cheer.getAmount() - 1);
        cheer.removePerson(person);
        cheerRepository.save(cheer);
    }
}
