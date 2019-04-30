package com.inter.car.backendchallenge.service.repository;

import com.inter.car.backendchallenge.service.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, String> {

    /**
     * Returns a car by trim only.
     *
     * @param trim - trim of a car
     * @return object of a Car
     */
    Car findOneByTrim(String trim);

    /**
     * Returns a cars by make and model.
     *
     * @param make  - the make of a car
     * @param model - the model of a car
     * @return list of a Cars
     */
    List<Car> findAllByMakeAndModel(String make, String model);

    /**
     * Returns a cars by make only.
     *
     * @param make - the make of a car
     * @return list of a Cars
     */
    List<Car> findAllByMake(String make);
}
