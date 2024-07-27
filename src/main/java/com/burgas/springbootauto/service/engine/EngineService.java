package com.burgas.springbootauto.service.engine;

import com.burgas.springbootauto.entity.engine.Engine;
import com.burgas.springbootauto.entity.engine.EngineCharacteristics;
import com.burgas.springbootauto.entity.engine.EngineEdition;
import com.burgas.springbootauto.repository.car.EquipmentRepository;
import com.burgas.springbootauto.repository.engine.EngineCharacteristicsRepository;
import com.burgas.springbootauto.repository.engine.EngineEditionRepository;
import com.burgas.springbootauto.repository.engine.EngineRepository;
import com.burgas.springbootauto.repository.person.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EngineService {

    private final EngineRepository engineRepository;
    private final PersonRepository personRepository;
    private final EngineCharacteristicsRepository engineCharacteristicsRepository;
    private final EngineEditionRepository engineEditionRepository;
    private final EquipmentRepository equipmentRepository;

    public List<Engine> findAll() {
        return engineRepository.findAll();
    }

    public Engine findById(Long id) {
        return engineRepository.findById(id).orElse(null);
    }

    public Engine findByName(String name) {
        return engineRepository.findEngineByName(name);
    }

    public List<Engine> searchEnginesByEngineBrandEditionCar(String search) {
        return engineRepository.searchEnginesByEngineBrandEditionCar(search).stream().distinct().toList();
    }

    public Page<Engine> searchEnginesByEngineBrandEditionCarNoSpaces(String search, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size).withSort(Sort.Direction.ASC, "name");
        return engineRepository.searchEnginesByEngineBrandEditionCarNoSpaces(search, pageRequest);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Long createEngine(Engine engine, EngineCharacteristics characteristics, EngineEdition edition) {
        Engine newEngine = Engine.builder().engineEdition(edition).name(engine.getName())
                .image(engine.getImage()).description(engine.getDescription()).fuel(engine.getFuel()).build();

        EngineCharacteristics engineCharacteristics = EngineCharacteristics.builder()
                .rpm(characteristics.getRpm()).volume(characteristics.getVolume()).torque(characteristics.getTorque())
                .cylinders(characteristics.getCylinders()).build();

        newEngine.addEngineCharacteristics(engineCharacteristics);
        engineRepository.save(newEngine);
        return engineRepository.findEngineByName(newEngine.getName()).getId();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public void update(Engine engine, Model model) {
        model.addAttribute("user",
                personRepository.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("characteristics",
                engineCharacteristicsRepository.findEngineCharacteristicsByEngineId(engine.getId()));
        engineRepository.save(engine);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, propagation = Propagation.REQUIRED)
    public Long delete(Long id) {
        Engine engine = engineRepository.findById(id).orElse(null);
        Long brandId = engineEditionRepository.searchEngineEditionByEngines(
                List.of(Objects.requireNonNull(engine))
        ).getBrand().getId();
        engine.removeEquipments(equipmentRepository.findAllByEngineId(id));
        engineRepository.save(engine);
        engineCharacteristicsRepository.deleteByEngineId(id);
        engineRepository.deleteById(id);
        return brandId;
    }
}
