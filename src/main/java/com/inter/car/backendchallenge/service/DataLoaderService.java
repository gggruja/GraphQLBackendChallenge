package com.inter.car.backendchallenge.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.inter.car.backendchallenge.service.entity.Car;
import com.inter.car.backendchallenge.service.logging.annotation.Log;
import com.inter.car.backendchallenge.service.repository.CarRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class DataLoaderService {

    @Log
    private static Logger log;

    @Value("classpath:tco-data.csv")
    Resource dataTco;

    @Autowired
    CarRepository carRepository;

    @PostConstruct()
    public void loadData() throws IOException {
        log.debug("Getting data from: {}", dataTco.getFilename());
        File file = dataTco.getFile();
        List<Car> cars = loadObjectList(Car.class, file.getName());
        carRepository.saveAll(cars);
    }

    private <T> List<T> loadObjectList(Class<T> type, String fileName) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            File file = new ClassPathResource(fileName).getFile();
            MappingIterator<T> readValues =
                    mapper.readerFor(type).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            log.error("Error occurred while loading object list from file " + fileName, e);
            return Collections.emptyList();
        }
    }

}
