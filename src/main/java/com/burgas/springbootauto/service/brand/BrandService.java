package com.burgas.springbootauto.service.brand;

import com.burgas.springbootauto.entity.brand.Brand;
import com.burgas.springbootauto.repository.brand.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;

    public Page<Brand> findAll(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }

    public Page<Brand>findPage(int page, int size) {
        Pageable pageRequest = PageRequest.of(page - 1, size).withSort(Sort.by(Sort.Direction.ASC,"title"));
        return brandRepository.findAll(pageRequest);
    }

    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    public Brand findById(Long id) {
        return brandRepository.findById(id).orElse(null);
    }

    public List<Brand> searchBrandByTitle(String title) {
        return brandRepository.searchBrandByTitleAndCar(title).stream().distinct().toList();
    }

    public Brand findBrandByTitle(String title) {
        return brandRepository.findBrandByTitle(title);
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
