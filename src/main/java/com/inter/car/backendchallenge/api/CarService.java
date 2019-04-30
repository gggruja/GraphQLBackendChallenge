package com.inter.car.backendchallenge.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CarService {

    String URI = "/cars";

    @PostMapping(value = URI)
    ResponseEntity<Object> getCars(@RequestBody String query);

}
