package com.burgas.springbootauto.service.transmission;

import com.burgas.springbootauto.entity.transmission.Gearbox;
import com.burgas.springbootauto.entity.transmission.Transmission;
import com.burgas.springbootauto.repository.brand.BrandRepository;
import com.burgas.springbootauto.repository.transmission.TransmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransmissionService {

    private final TransmissionRepository transmissionRepository;
    private final BrandRepository brandRepository;

    public List<Transmission> findAll() {
        return transmissionRepository.findAll();
    }

    public Transmission findById(Long id) {
        return transmissionRepository.findById(id).orElse(null);
    }

    public Transmission findByName(String name) {
        return transmissionRepository.findTransmissionByName(name);
    }

    public List<Transmission> searchTransmissionsByNeighbourNames(String search) {
        return transmissionRepository.searchTransmissionsByNeighbourNames(search).stream().distinct().toList();
    }

    public List<Transmission> searchTransmissionsByNeighbourNamesNoSpaces(String search) {
        return transmissionRepository.searchTransmissionsByNeighbourNamesNoSpaces(search).stream().distinct().toList();
    }

    @Transactional
    public void save(Transmission transmission) {
        transmissionRepository.save(transmission);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Long addTransmissionToBrand(Long brandId, Transmission transmission, Gearbox gearbox) {
        Transmission newTransmission = Transmission.builder().name(transmission.getName())
                .brand(brandRepository.findById(brandId).orElse(null))
                .gearbox(gearbox).ratio(transmission.getRatio()).finalRatio(transmission.getFinalRatio())
                .driveType(transmission.getDriveType()).image(transmission.getImage()).description(transmission.getDescription())
                .build();
        transmissionRepository.save(newTransmission);
        return transmissionRepository.findTransmissionByName(newTransmission.getName()).getId();
    }

    @Transactional
    public void update(Transmission transmission) {
        transmissionRepository.save(transmission);
    }

    @Transactional
    public void delete(Long id) {
        transmissionRepository.deleteById(id);
    }
}
