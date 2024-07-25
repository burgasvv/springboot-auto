package com.burgas.springbootauto.service.brand;

import com.burgas.springbootauto.entity.brand.Brand;
import com.burgas.springbootauto.repository.brand.BrandRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BrandService {

    private final BrandRepository brandRepository;

    public Page<Brand> findAll(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }

    public Page<Brand>findPage(int page, int size) {
        Pageable pageRequest = PageRequest.of(page - 1, size).withSort(Sort.by(Sort.Direction.ASC,"title"));
        return brandRepository.findAll(pageRequest);
    }

    public Page<Brand>findBrandByBrandName(String brandName, int page, int size) {
        Pageable pageRequest = PageRequest.of(page - 1, size).withSort(Sort.by(Sort.Direction.ASC,"title"));
        return brandRepository.findBrandsByBrandName(brandName, pageRequest);
    }

    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    public Brand findById(Long id) {
        return brandRepository.findById(id).orElse(null);
    }

    public List<Brand> searchBrandByTitle(String title) {
        return brandRepository.searchBrandsByTitleAndCar(title);
    }

    public Brand findBrandByTitle(String title) {
        return brandRepository.findBrandByTitle(title);
    }

    @SneakyThrows
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void save(Brand brand) {
        brandRepository.save(brand);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void update(Brand brand) {
        brandRepository.save(brand);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRES_NEW)
    public void delete(Long id) {
        Brand brand = brandRepository.findById(id).orElse(null);
        Objects.requireNonNull(brand).getCars().forEach(car -> car.setBrand(null));
        brand.getEngineEditions()
                .forEach(engineEdition -> engineEdition.getEngines()
                        .forEach(engine -> engine.getEquipments().forEach(equipment -> equipment.setEngine(null))));
        brand.getTransmissions()
                        .forEach(transmission -> transmission.getEquipments()
                        .forEach(equipment -> equipment.setTransmission(null)));
        brand.getTurbochargers()
                        .forEach(turbocharger -> turbocharger.getEquipments()
                                .forEach(equipment -> equipment.setTurbocharger(null)));
        brandRepository.save(brand);
        brandRepository.deleteById(brand.getId());
    }
}
