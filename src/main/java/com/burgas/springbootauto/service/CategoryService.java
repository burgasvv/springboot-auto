package com.burgas.springbootauto.service;

import com.burgas.springbootauto.entity.car.Category;
import com.burgas.springbootauto.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Transactional
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public void update(Category category) {
        categoryRepository.save(category);
    }

    @Transactional
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
