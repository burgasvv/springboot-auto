package com.burgas.springbootauto.service.transmission;

import com.burgas.springbootauto.entity.transmission.Gearbox;
import com.burgas.springbootauto.repository.brand.BrandRepository;
import com.burgas.springbootauto.repository.transmission.GearboxRepository;
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
public class GearboxService {

    private final GearboxRepository gearboxRepository;
    private final BrandRepository brandRepository;

    public List<Gearbox> findAll() {
        return gearboxRepository.findAll();
    }

    public Gearbox findById(Long id) {
        return gearboxRepository.findById(id).orElse(null);
    }

    public Gearbox findByName(String name) {
        return gearboxRepository.findGearboxByName(name);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Long createGearbox(Gearbox gearbox, HttpServletRequest servletRequest) {
        String[] selectedBrands = servletRequest.getParameterValues("selectedBrands");
        Gearbox newGearbox = new Gearbox();
        newGearbox.setName(gearbox.getName());
        newGearbox.setStairs(gearbox.getStairs());
        newGearbox.setImage(gearbox.getImage());
        Arrays.stream(selectedBrands).toList().iterator().forEachRemaining(s ->
                newGearbox.addBrand(brandRepository.findById(Long.valueOf(s)).orElse(null))
        );
        gearboxRepository.save(newGearbox);
        return gearboxRepository.findGearboxByName(newGearbox.getName()).getId();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Long addGearboxToBrand(Long brandId, Gearbox gearbox) {
        Gearbox newGearbox = new Gearbox();
        newGearbox.setName(gearbox.getName());
        newGearbox.setStairs(gearbox.getStairs());
        newGearbox.setImage(gearbox.getImage());
        newGearbox.addBrand(brandRepository.findById(brandId).orElse(null));
        gearboxRepository.save(newGearbox);
        return gearboxRepository.findGearboxByName(newGearbox.getName()).getId();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void update(Gearbox gearbox) {
        gearboxRepository.save(gearbox);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        Gearbox gearbox = gearboxRepository.findById(id).orElse(null);
        Objects.requireNonNull(gearbox).getBrands().forEach(brand -> brand.getGearboxes().remove(gearbox));
        Objects.requireNonNull(gearbox).getTransmissions()
                        .forEach(transmission -> transmission.getEquipments()
                                .forEach(equipment -> equipment.setTransmission(null)));
        gearboxRepository.save(gearbox);
        gearboxRepository.deleteById(id);
    }
}
