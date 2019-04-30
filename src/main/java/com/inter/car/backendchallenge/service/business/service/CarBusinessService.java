package com.inter.car.backendchallenge.service.business.service;

import com.inter.car.backendchallenge.service.entity.Car;

import java.util.List;

/**
 * Business service for cars inside of a database
 */
public interface CarBusinessService {

    /**
     * Returns a cars from DB
     *
     * @return list of a Cars
     */
    List<Car> getAllCarsFromDB();
}
