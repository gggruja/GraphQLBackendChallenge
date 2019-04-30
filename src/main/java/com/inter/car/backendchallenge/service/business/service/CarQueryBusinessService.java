package com.inter.car.backendchallenge.service.business.service;

import com.inter.car.backendchallenge.service.entity.Car;

import java.util.List;

/**
 * Business service for poxing the Car Query API
 */
public interface CarQueryBusinessService {

    /**
     * Returns a cars by command (getMakes, getModels, etc.)
     *
     * @param cmd - command (getMakes, getModels, etc.)
     * @return list of a Cars
     */
    List<Car> getCarsTCObyMake(String cmd);

    /**
     * Returns a cars by command (getMakes, getModels, etc.)
     *
     * @param cmd   - command (getMakes, getModels, etc.)
     * @param model - model of a car
     * @return list of a Cars
     */
    List<Car> getCarsTCOByModel(String cmd, String model);
}
