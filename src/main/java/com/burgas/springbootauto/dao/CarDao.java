package com.burgas.springbootauto.dao;

import com.burgas.springbootauto.mapper.CarRowMapper;
import com.burgas.springbootauto.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CarDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Car> findAll() {
        return jdbcTemplate.query("select * from cars", new CarRowMapper());
    }

    public Car find(int id) {
        return jdbcTemplate.queryForObject("select * from cars where cars.id = ?", new CarRowMapper(), id);
    }

    public void save(Car car) {
        jdbcTemplate.update("insert into cars(title, brand_id, class_id, category_id, image, webpage, description, price) " +
                        "VALUES (?,?,?,?,?,?,?,?)",
                car.getTitle(), car.getBrandId(), car.getClassId(), car.getCategoryId(),
                car.getImage(), car.getWebpage(), car.getDescription(), car.getPrice()
        );
    }

    public void update(Car car) {
        jdbcTemplate.update("update cars set title=?,brand_id=?,class_id=?,category_id=?,image=?,webpage=?,description=?,price=? where id=?",
                car.getTitle(), car.getBrandId(), car.getClassId(), car.getCategoryId(),
                car.getImage(), car.getWebpage(), car.getDescription(), car.getPrice(), car.getId()
        );
    }

    public void delete(Car car) {
        jdbcTemplate.update("delete from cars where id=?", car.getId());
    }

    public List<Car> searchByTagWord(String search) {
        //noinspection SqlSourceToSinkFlow
        return jdbcTemplate.query("select * from cars join cars_tags ct on cars.id = ct.car_id " +
                "join tags t on t.id = ct.tag_id where concat(name,' ') like '%"+search+"%'", new CarRowMapper());
    }

    public List<Car> searchFull(String search) {
        //noinspection SqlSourceToSinkFlow
        return jdbcTemplate.query("select * from cars\n" +
                "join public.brands b on cars.brand_id = b.id\n" +
                "join public.categories c on c.id = cars.category_id\n" +
                "join public.cars_tags ct on cars.id = ct.car_id\n" +
                "join public.tags t on t.id = ct.tag_id\n" +
                "join public.classes c2 on c2.id = cars.class_id\n" +
                "where concat(cars.title,' ',cars.price,' ',b.title,' ',c.name,' ',c2.name,' ',t.name,' ') ilike '%"+search+"%'",
                new CarRowMapper());
    }
}
