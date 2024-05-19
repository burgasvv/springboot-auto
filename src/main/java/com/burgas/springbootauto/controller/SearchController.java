package com.burgas.springbootauto.controller;

import com.burgas.springbootauto.service.brand.BrandService;
import com.burgas.springbootauto.service.car.CarService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
    public String search(@RequestParam("search") String search , Model model) {
        search = search.replaceAll(" ", "");
        model.addAttribute("cars", carService.searchCarsByAllNames(search));
        model.addAttribute("searchBrands", brandService.searchBrandByTitle(search));
        model.addAttribute("search", search);
        return "search/global";
    }
}
