package com.burgas.springbootauto.service.car;

import com.burgas.springbootauto.entity.car.DriveUnit;
import com.burgas.springbootauto.repository.car.DriveUnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriveUnitService {

    private final DriveUnitRepository driveUnitRepository;

    public List<DriveUnit> findAll() {
        return driveUnitRepository.findAll();
    }

    public DriveUnit findById(Long id) {
        return driveUnitRepository.findById(id).orElse(null);
    }
}
