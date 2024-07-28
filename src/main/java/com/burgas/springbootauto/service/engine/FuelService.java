package com.burgas.springbootauto.service.engine;

import com.burgas.springbootauto.entity.engine.Fuel;
import com.burgas.springbootauto.repository.engine.FuelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuelService {

    private final FuelRepository fuelRepository;

    public List<Fuel> findAll() {
        return fuelRepository.findAll();
    }
}
