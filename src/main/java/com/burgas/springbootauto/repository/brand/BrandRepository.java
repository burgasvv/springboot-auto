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
                    select distinct b.* from brand b
                    join car c on b.id = c.brand_id
                    where concat(b.title,' ',c.title,' ',b.title,' ',c.title,' ',c.title,' ',b.title,' ',
                          ' ',c.title,' ',b.title,' ',c.title,' ') ilike concat('%',?1,'%')"""
    )
    List<Brand> searchBrandsByTitleAndCar(String brandName);

    Brand findBrandByTitle(String title);
}
