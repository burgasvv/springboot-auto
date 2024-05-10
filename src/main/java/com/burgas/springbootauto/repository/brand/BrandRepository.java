package com.burgas.springbootauto.repository.brand;

import com.burgas.springbootauto.entity.brand.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    List<Brand> searchBrandByTitleIgnoreCase(String brandName);
}
