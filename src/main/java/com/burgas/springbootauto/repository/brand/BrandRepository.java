package com.burgas.springbootauto.repository.brand;

import com.burgas.springbootauto.entity.brand.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query(
            nativeQuery = true,
            value = "select br.* from brand br where concat(title,' ') ilike concat('%',?1,'%')"
    )
    List<Brand> searchBrandByTitle(String brandName);
}
