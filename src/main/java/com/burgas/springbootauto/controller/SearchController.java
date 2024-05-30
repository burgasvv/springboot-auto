package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.service.brand.BrandService;
import com.burgas.springbootauto.service.car.CarService;
import com.burgas.springbootauto.service.engine.EngineService;
import com.burgas.springbootauto.service.person.PersonService;
import com.burgas.springbootauto.service.transmission.TransmissionService;
import com.burgas.springbootauto.service.turbocharging.TurbochargerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final CarService carService;
    private final BrandService brandService;
    private final EngineService engineService;
    private final TransmissionService transmissionService;
    private final TurbochargerService turbochargerService;
    private final PersonService personService;

    @GetMapping
    public String search(@RequestParam("search") String search, Model model, HttpServletRequest request) {
        model.addAttribute("user",
                personService.findPersonByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
        );
        model.addAttribute("brands", brandService.searchBrandByTitle(search));
        model.addAttribute("cars", carService.searchCarsByAllNames(search));
        model.addAttribute("engines", engineService.searchEnginesByEngineBrandEditionCar(search));
        model.addAttribute("transmissions", transmissionService.searchTransmissionsByNeighbourNames(search));
        model.addAttribute("turbochargers", turbochargerService.searchTurbochargersByNeighbourNames(search));
        model.addAttribute("selectObjects", request.getParameterValues("selectObjects"));
        model.addAttribute("search", search);
        return "search/global";
    }
}
