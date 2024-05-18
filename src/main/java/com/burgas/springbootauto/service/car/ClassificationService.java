package com.burgas.springbootauto.service.car;

import com.burgas.springbootauto.entity.car.Classification;
import com.burgas.springbootauto.repository.car.ClassificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Classification findClassificationByName(String name) {
        return classificationRepository.findClassificationByName(name);
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
        classificationRepository.deleteById(id);
    }
}
