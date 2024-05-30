package com.burgas.springbootauto.repository.brand;

import com.burgas.springbootauto.entity.brand.Brand;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Override
    @NotNull
    Page<Brand> findAll(@NotNull Pageable pageable);

    @Query(
            nativeQuery = true,
            value = """
                    select br.* from brand br
                    join car c on br.id = c.brand_id
                    join public.engine_edition ee on br.id = ee.brand_id
                    join public.engine e on ee.id = e.edition_id
                    where concat(br.title,' ',c.title,' ',ee.name,' ',br.title,' ',c.title,' ',ee.name,' ',c.title,' ',br.title,' ',e.name,' ',
                          ee.name,' ',c.title,' ',e.name,' ',br.title,' ',ee.name,' ',e.name,' ',c.title,' ') ilike concat('%',?1,'%')"""
    )
    List<Brand> searchBrandByTitle(String brandName);

    Brand findBrandByTitle(String title);
}
