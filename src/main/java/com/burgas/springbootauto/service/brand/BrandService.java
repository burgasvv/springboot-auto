package com.burgas.springbootauto.service.brand;

import com.burgas.springbootauto.entity.brand.Brand;
import com.burgas.springbootauto.entity.image.Image;
import com.burgas.springbootauto.repository.brand.BrandRepository;
import com.burgas.springbootauto.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final ImageService imageService;

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

    @SneakyThrows
    @Transactional
    public void save(Brand brand, MultipartFile file) {
        if (imageService.findByName(file.getOriginalFilename()) != null) {
            return;
        }
        if (file.getSize() != 0) {
            Image image = new Image();
            image.setName(file.getOriginalFilename());
            image.setPreview(true);
            image.setData(file.getBytes());
            imageService.save(image);
            brand.setImage(image);
        }
        brandRepository.save(brand);
    }

    @Transactional
    public void update(Brand brand) {
        brandRepository.save(brand);
    }

    @Transactional
    public void delete(Brand brand) {
        brandRepository.deleteById(brand.getId());
    }

    @Transactional
    public void removeImage(Brand brand) {
        Image image = brand.getImage();
        brand.setImage(null);
        brandRepository.save(brand);
        imageService.delete(image);
    }
}
