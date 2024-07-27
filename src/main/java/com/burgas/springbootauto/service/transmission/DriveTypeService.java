package com.burgas.springbootauto.service.transmission;

import com.burgas.springbootauto.entity.transmission.DriveType;
import com.burgas.springbootauto.repository.transmission.DriveTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriveTypeService {

    private final DriveTypeRepository driveTypeRepository;

    public List<DriveType> findAll() {
        return driveTypeRepository.findAll();
    }
}
