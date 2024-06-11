package com.burgas.springbootauto.service.car;

import com.burgas.springbootauto.entity.car.Classification;
import com.burgas.springbootauto.repository.car.ClassificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClassificationService {

    private final ClassificationRepository classificationRepository;

    public List<Classification> findAll() {
        return classificationRepository.findAll();
    }

    public Classification findById(Long id) {
        return classificationRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Classification classification) {
        classificationRepository.save(classification);
    }

    @Transactional
    public void update(Classification classification) {
        classificationRepository.save(classification);
    }

    @Transactional
    public void delete(Long id) {
        Classification classification = classificationRepository.findById(id).orElse(null);
        Objects.requireNonNull(classification).getCars().forEach(car -> car.setClassification(null));
        classificationRepository.save(classification);
        classificationRepository.deleteById(id);
    }
}
