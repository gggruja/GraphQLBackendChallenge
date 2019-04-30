package com.inter.car.backendchallenge.service.controller;

import com.inter.car.backendchallenge.api.CarService;
import com.inter.car.backendchallenge.service.GraphQLService;
import com.inter.car.backendchallenge.service.entity.Car;
import com.inter.car.backendchallenge.service.logging.annotation.Log;
import graphql.ExecutionResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "graph-ql", description = "Cars controller")
public class CarController implements CarService {

    @Log
    private static Logger log;

    @Autowired
    GraphQLService graphQLService;

    @ApiOperation(value = "Retrieves all cars by given query")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully read Car", response = Car.class),
            @ApiResponse(code = 400, message = "Invalid input"),
            @ApiResponse(code = 401, message = "You are not authorized to view cars"),
            @ApiResponse(code = 403, message = "Accessing the cars you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The cars you were trying to reach are not found"),
            @ApiResponse(code = 500, message = "We encountered an internal error. Please try again")
    })
    @Override
    public ResponseEntity<Object> getCars(@RequestBody String query) {

        log.debug("Getting cars with a query: {}", query);

        ExecutionResult execute = graphQLService.getGraphQL().execute(query);
        return new ResponseEntity<>(execute, HttpStatus.OK);
    }
}
