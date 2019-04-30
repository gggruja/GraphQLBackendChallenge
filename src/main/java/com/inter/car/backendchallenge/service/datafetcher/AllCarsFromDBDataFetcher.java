package com.inter.car.backendchallenge.service.datafetcher;

import com.inter.car.backendchallenge.service.business.service.CarBusinessService;
import com.inter.car.backendchallenge.service.entity.Car;
import com.inter.car.backendchallenge.service.logging.annotation.Log;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Listener for "cars" query from POST method of a GraphQL and retrieves Car's data
 */
@Component
public class AllCarsFromDBDataFetcher implements DataFetcher<List<Car>> {

    @Log
    private static Logger log;

    @Autowired
    private CarBusinessService carBusinessService;

    @Override
    public List<Car> get(DataFetchingEnvironment dataFetchingEnvironment) {

        log.debug("Fetching data from AllCarsFromDBDataFetcher");
        return carBusinessService.getAllCarsFromDB();
    }
}
