package com.burgas.springbootauto.service.car;

import com.burgas.springbootauto.entity.car.Car;
import com.burgas.springbootauto.entity.car.Tag;
import com.burgas.springbootauto.repository.car.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public List<Tag> searchTagsByCars(Car car) {
        return tagRepository.searchTagsByCars(List.of(car));
    }

    public Tag findById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Tag tag) {
        if (tagRepository.findByName(tag.getName()) != null) {
            return;
        }
        tagRepository.save(tag);
    }

    @Transactional
    public void update(Tag tag) {
        if (tagRepository.findByName(tag.getName()) != null) {
            return;
        }
        tagRepository.save(tag);
    }

    @Transactional
    public void delete(Long id) {
        tagRepository.deleteById(id);
    }
}
