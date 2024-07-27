package com.burgas.springbootauto.service.car;

import com.burgas.springbootauto.entity.car.Category;
import com.burgas.springbootauto.repository.car.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void save(Category category) {
        categoryRepository.save(category);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void update(Category category) {
        categoryRepository.save(category);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        Objects.requireNonNull(category).getCars().forEach(car -> car.setCategory(null));
        categoryRepository.save(category);
        categoryRepository.deleteById(id);
    }
}
