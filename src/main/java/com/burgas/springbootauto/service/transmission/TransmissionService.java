package com.burgas.springbootauto.service.transmission;

import com.burgas.springbootauto.entity.transmission.Transmission;
import com.burgas.springbootauto.repository.transmission.TransmissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransmissionService {

    private final TransmissionRepository transmissionRepository;

    public TransmissionService(TransmissionRepository transmissionRepository) {
        this.transmissionRepository = transmissionRepository;
    }

    public List<Transmission> findAll() {
        return transmissionRepository.findAll();
    }

    public Transmission findById(Long id) {
        return transmissionRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Transmission transmission) {
        transmissionRepository.save(transmission);
    }

    @Transactional
    public void update(Transmission transmission) {
        transmissionRepository.save(transmission);
    }

    public void delete(Long id) {
        transmissionRepository.deleteById(id);
    }
}
