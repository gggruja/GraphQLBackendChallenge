package com.inter.car.backendchallenge.service.business.service.impl;

import com.inter.car.backendchallenge.service.business.service.CarBusinessService;
import com.inter.car.backendchallenge.service.entity.Car;
import com.inter.car.backendchallenge.service.logging.annotation.Log;
import com.inter.car.backendchallenge.service.repository.CarRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of business service for cars inside of a database
 */
@Service
public class CarBusinessServiceImpl implements CarBusinessService {

    @Log
    private static Logger log;

    @Autowired
    private CarRepository carRepository;

    /**
     * Implementation of  cars from DB
     *
     * @return list of a Cars
     */
    @Override
    public List<Car> getAllCarsFromDB() {
        log.debug("Getting all cars from DB");
        return carRepository.findAll();
    }
}
