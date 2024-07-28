package com.burgas.springbootauto.service.car;

import com.burgas.springbootauto.entity.car.Tag;
import com.burgas.springbootauto.repository.car.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Tag findById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }
}
