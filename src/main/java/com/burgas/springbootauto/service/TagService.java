package com.burgas.springbootauto.service;

import com.burgas.springbootauto.entity.Car;
import com.burgas.springbootauto.entity.Tag;
import com.burgas.springbootauto.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> findAll() {
        return tagRepository.findAll().stream().toList();
    }

    public List<Tag> searchTagsByCars(Car car) {
        return tagRepository.searchTagsByCars(List.of(car));
    }

    public Tag findById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Tag tag) {
        tagRepository.save(tag);
    }

    @Transactional
    public void update(Tag tag) {
        tagRepository.save(tag);
    }

    @Transactional
    public void delete(Long id) {
        tagRepository.deleteById(id);
    }
}
