package com.burgas.springbootauto.service.turbocharging;

import com.burgas.springbootauto.entity.turbocharging.TurboType;
import com.burgas.springbootauto.repository.brand.BrandRepository;
import com.burgas.springbootauto.repository.turbocharging.TurboTypeRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TurboTypeService {

    private final TurboTypeRepository turboTypeRepository;
    private final BrandRepository brandRepository;

    public List<TurboType> findAll() {
        return turboTypeRepository.findAll();
    }

    public TurboType findById(Long id) {
        return turboTypeRepository.findById(id).orElse(null);
    }

    public TurboType findByName(String name) {
        return turboTypeRepository.findByName(name);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Long save(TurboType turboType, HttpServletRequest request) {
        String[] turboTypeBrands = request.getParameterValues("selectedBrands");
        TurboType newTurboType = new TurboType();
        newTurboType.setName(turboType.getName());
        newTurboType.setImage(turboType.getImage());
        newTurboType.setDescription(turboType.getDescription());
        Arrays.stream(turboTypeBrands).toList().forEach(s ->
                newTurboType.addBrand(brandRepository.findById(Long.parseLong(s)).orElse(null))
        );
        turboTypeRepository.save(turboType);
        return turboTypeRepository.findByName(newTurboType.getName()).getId();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Long addTurboTypeToBrand(Long brandId, TurboType turboType) {
        TurboType newTurboType = new TurboType();
        newTurboType.setName(turboType.getName());
        newTurboType.setImage(turboType.getImage());
        newTurboType.setDescription(turboType.getDescription());
        newTurboType.addBrand(brandRepository.findById(brandId).orElse(null));
        turboTypeRepository.save(newTurboType);
        return turboTypeRepository.findByName(newTurboType.getName()).getId();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void update(TurboType turboType) {
        turboTypeRepository.save(turboType);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        TurboType turboType = turboTypeRepository.findById(id).orElse(null);
        Objects.requireNonNull(turboType).getBrands()
                        .forEach(brand -> brand.getTurboTypes().remove(turboType));
        turboType.getTurbochargers().forEach(turbocharger -> turbocharger.getEquipments()
                .forEach(equipment -> equipment.setTurbocharger(null)));
        turboTypeRepository.deleteById(id);
    }
}
