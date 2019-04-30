package com.inter.car.backendchallenge.service.business.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inter.car.backendchallenge.service.business.service.CarQueryBusinessService;
import com.inter.car.backendchallenge.service.business.service.impl.utils.UrlApiValues;
import com.inter.car.backendchallenge.service.configuration.CarQueryConfiguration;
import com.inter.car.backendchallenge.service.entity.Car;
import com.inter.car.backendchallenge.service.logging.annotation.Log;
import com.inter.car.backendchallenge.service.repository.CarRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of a business service for poxing the Car Query API
 */
@Service
public class CarQueryBusinessServiceImpl implements CarQueryBusinessService {

    @Log
    private static Logger log;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarQueryConfiguration carQueryConfiguration;

    /**
     * Implementation of business service which returns a cars by command (getMakes, getModels, etc.)
     *
     * @param cmd - command (getMakes, getModels, etc.)
     * @return list of a Cars
     */
    @Override
    public List<Car> getCarsTCObyMake(String cmd) {

        log.debug("Getting cars TCO by cmd {}", cmd);

        String apiCMD = checkAndRetrieveCMD(cmd);
        String apiMake = "";

        JsonNode jsonNode = jsonNodeFromRequest(apiCMD, apiMake);

        if (jsonNode == null) {
            return Collections.emptyList();
        }

        List<String> valuesAsText = jsonNode.get(UrlApiValues.JSON_MAKES)
                .findValuesAsText(UrlApiValues.JSON_MAKE_DISPLAY);

        List<Car> list = new ArrayList<>();

        valuesAsText.stream().map(make -> carRepository.findAllByMake(make)).forEach(list::addAll);

        return list.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Implementation of business service which returns a cars by command (getMakes, getModels, etc.)
     *
     * @param cmd   - command (getMakes, getModels, etc.)
     * @param model - model of a car
     * @return list of a Cars
     */
    @Override
    public List<Car> getCarsTCOByModel(String cmd, String model) {

        log.debug("Getting cars TCO by cmd {} and by model {}", cmd, model);

        String apiCMD = checkAndRetrieveCMD(cmd);
        String apiModel = checkAndRetrieveModelFilter(model);

        JsonNode jsonNode = jsonNodeFromRequest(apiCMD, apiModel);

        if (jsonNode == null) {
            return Collections.emptyList();
        }

        List<String> valuesAsText = jsonNode.get(UrlApiValues.JSON_MODEL)
                .findValuesAsText(UrlApiValues.JSON_MODEL_NAME);

        List<Car> list = new ArrayList<>();

        valuesAsText.stream().map(make -> carRepository.findAllByMakeAndModel(model, make)).forEach(list::addAll);

        return list.stream().distinct().collect(Collectors.toList());
    }

    private JsonNode jsonNodeFromRequest(String apiCMD, String apiFilter) {

        log.debug("Retrieve JSON node from request with CMD: {} and Filter: {}", apiCMD, apiFilter);

        HttpEntity<String> entity = createHttpEntity();

        String url = createURL(apiCMD, apiFilter);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        Assert.notNull(response.getBody(), "There is no response from API");

        JsonNode jsonNode = parseBodyToJson(response.getBody());

        return jsonNode;
    }

    private String createURL(String apiCMD, String apiFilter) {

        log.debug("Creating URL with CMD: {} and Filter: {}", apiCMD, apiFilter);

        return carQueryConfiguration.getUrl() + apiCMD + apiFilter;
    }

    private String checkAndRetrieveModelFilter(String model) {

        log.debug("Model: {}", model);

        if (model == null) {
            return "";
        }

        return UrlApiValues.PARAM_MAKE + model;
    }

    private String checkAndRetrieveCMD(String cmd) {

        log.debug("CMD: {}", cmd);
        Assert.notNull(cmd, "There is no CMD specifies the API function");

        switch (cmd) {
            case UrlApiValues.COMMAND_GET_MODELS:
                return UrlApiValues.COMMAND_GET_MODELS;
            case UrlApiValues.COMMAND_GET_MAKES:
                return UrlApiValues.COMMAND_GET_MAKES;
            default:
                throw new IllegalArgumentException();
        }

    }

    private JsonNode parseBodyToJson(String body) {

        log.debug("Parsing body");

        String json = body.substring(2);
        json = json.substring(0, json.length() - 2);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(json);
        } catch (IOException e) {
            log.error("Error parsing response string {}", body);
            return null;
        }
    }

    private HttpEntity<String> createHttpEntity() {

        log.debug("Create HTTP entry");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(UrlApiValues.HEADER_NAME, UrlApiValues.HEADER_VALUE);
        return new org.springframework.http.HttpEntity<>(headers);
    }
}
