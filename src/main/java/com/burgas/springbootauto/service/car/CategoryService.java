package com.burgas.springbootauto.service.car;

import com.burgas.springbootauto.entity.car.Category;
import com.burgas.springbootauto.entity.image.Image;
import com.burgas.springbootauto.repository.car.CategoryRepository;
import com.burgas.springbootauto.service.image.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ImageService imageService;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @SneakyThrows
    @Transactional
    public void save(Category category, MultipartFile file) {
        if (imageService.findByName(file.getOriginalFilename()) != null) {
            return;
        }
        if (file.getSize() != 0) {
            Image image = Image.builder()
                    .name(file.getOriginalFilename())
                    .isPreview(true)
                    .data(file.getBytes())
                    .build();
            imageService.save(image);
            category.setImage(image);
        }
        categoryRepository.save(category);
    }

    @Transactional
    public void update(Category category) {
        categoryRepository.save(category);
    }

    @Transactional
    public void delete(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        Objects.requireNonNull(category).getCars().forEach(car -> car.setCategory(null));
        categoryRepository.save(category);
        categoryRepository.deleteById(id);
    }

    @Transactional
    public void removeImage(Category category) {
        Image image = category.getImage();
        category.setImage(null);
        categoryRepository.save(category);
        imageService.delete(image);
    }
}
