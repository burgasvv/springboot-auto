package com.burgas.springbootauto.service;

import com.burgas.springbootauto.entity.Classification;
import com.burgas.springbootauto.repository.ClassificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClassificationService {

    private final ClassificationRepository classificationRepository;

    public ClassificationService(ClassificationRepository classificationRepository) {
        this.classificationRepository = classificationRepository;
    }

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
        classificationRepository.deleteById(id);
    }
}
