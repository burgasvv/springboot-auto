package com.burgas.springbootauto.service;

import com.burgas.springbootauto.entity.engine.Fuel;
import com.burgas.springbootauto.repository.FuelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FuelService {

    private final FuelRepository fuelRepository;

    public FuelService(FuelRepository fuelRepository) {
        this.fuelRepository = fuelRepository;
    }

    public List<Fuel> findAll() {
        return fuelRepository.findAll();
    }

    public Fuel findById(Long id) {
        return fuelRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Fuel fuel) {
        fuelRepository.save(fuel);
    }

    @Transactional
    public void update(Fuel fuel) {
        fuelRepository.save(fuel);
    }

    @Transactional
    public void delete(Long id) {
        fuelRepository.deleteById(id);
    }
}
