package com.burgas.springbootauto.service.transmission;

import com.burgas.springbootauto.entity.transmission.DriveType;
import com.burgas.springbootauto.repository.transmission.DriveTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriveTypeService {

    private final DriveTypeRepository driveTypeRepository;

    public List<DriveType> findAll() {
        return driveTypeRepository.findAll();
    }

    public DriveType findById(Long id) {
        return driveTypeRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(DriveType driveType) {
        driveTypeRepository.save(driveType);
    }

    @Transactional
    public void update(DriveType driveType) {
        driveTypeRepository.save(driveType);
    }

    @Transactional
    public void delete(Long id) {
        driveTypeRepository.deleteById(id);
    }
}
