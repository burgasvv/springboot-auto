package com.burgas.springbootauto.repository.car;

import com.burgas.springbootauto.entity.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> searchCarsByCategoryId(Long id);

    List<Car> searchCarsByClassificationId(Long id);

    @Query(
            nativeQuery = true,
            value = """
                    select c.* from car c join car_tags ct on c.id = ct.car_id join tag t on t.id = ct.tag_id
                    where concat(name,' ') ilike concat('%',?1,'%')"""
    )
    List<Car> searchCarsByTagName(String tagName);

    @Query(
            nativeQuery = true,
            value = """
                    select c.* from car c
                    join brand b on b.id = c.brand_id
                    join category c2 on c2.id = c.category_id
                    join classification c3 on c3.id = c.classification_id
                    join car_tags ct on c.id = ct.car_id
                    join tag t on t.id = ct.tag_id
                    where concat(b.title,' ',c3.name,' ',c2.name,' ',b.title,' ',c2.name,' ',c.title,' ',t.name,' ',
                        c.title,' ',c3.name,' ',b.title,' ',c.title,' ',b.title,' ',t.name,' ',c3.name,' ',c.title,' ',c2.name,' ',c3.name,' ',
                        t.name,' ',c2.name,' ',t.name,' ',b.title,' ',b.title,' ',c.title,' ',t.name,' ',t.name,' ',t.name,' ') 
                              ilike concat('%',?1,'%')"""
    )
    List<Car> searchCarsByAllNames(String search);

    @Query(
            nativeQuery = true,
            value = """
                    select c.* from car c join brand b on b.id = c.brand_id
                                          join category c2 on c2.id = c.category_id
                                          join classification c3 on c3.id = c.classification_id
                                          join car_tags ct on c.id = ct.car_id
                                          join tag t on t.id = ct.tag_id
                    where
                        concat(b.title,c3.name,c2.name,b.title,c2.name,c.title,t.name,
                        c.title,c3.name,b.title,c.title,b.title,t.name,c3.name,c.title,c2.name,c3.name,
                        t.name,' ',c2.name,' ',t.name,' ',b.title)
                              ilike concat('%',?1,'%')"""
    )
    List<Car> searchCarsWithNoSpaces(String search);
}
