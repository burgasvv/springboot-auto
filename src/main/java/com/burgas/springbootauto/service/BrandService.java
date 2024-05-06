package com.burgas.springbootauto.service;

import com.burgas.springbootauto.entity.brand.Brand;
import com.burgas.springbootauto.repository.BrandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BrandService {

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    public Brand findById(Long id) {
        return brandRepository.findById(id).orElse(null);
    }

    public List<Brand> searchBrandByTitle(String title) {
        return brandRepository.searchBrandByTitleIgnoreCase(title);
    }

    @Transactional
    public void save(Brand brand) {
        brandRepository.save(brand);
    }

    @Transactional
    public void update(Brand brand) {
        brandRepository.save(brand);
    }

    @Transactional
    public void delete(Long id) {
        brandRepository.deleteById(id);
    }
}
