package com.inter.car.backendchallenge.service.business.service.impl;

import com.inter.car.backendchallenge.service.configuration.LoggingConfiguration;
import com.inter.car.backendchallenge.service.entity.Car;
import com.inter.car.backendchallenge.service.repository.CarRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { CarBusinessServiceImpl.class })
@Import(LoggingConfiguration.class)
public class CarBusinessServiceImplTest {

    private static final String MAKES_CAR_1 = "Acura";
    private static final String MODELS_CAR_1 = "ILX";
    private static final int YEARS_CAR_1 = 2018;
    private static final int TCO_CAR_1 = 5184;
    private static final String TRIM_CAR_1 = "ILX,4dr Sedan (2.0L 4cyl 5A)";

    private static final String MAKES_CAR_2 = "Acura";
    private static final String MODELS_CAR_2 = "RDX";
    private static final int YEARS_CAR_2 = 2018;
    private static final int TCO_CAR_2 = 5382;
    private static final String TRIM_CAR_2 = "4dr SUV (3.5L 6cyl 6A)";

    @MockBean
    CarRepository mockCarRepository;

    @Autowired
    CarBusinessServiceImpl carBusinessService;

    public List<Car> createListOfCars() {

        Car car1 = new Car();
        car1.setMake(MAKES_CAR_1);
        car1.setModel(MODELS_CAR_1);
        car1.setTrim(TRIM_CAR_1);
        car1.setYear(YEARS_CAR_1);
        car1.setTco(TCO_CAR_1);

        Car car2 = new Car();
        car2.setMake(MAKES_CAR_2);
        car2.setModel(MODELS_CAR_2);
        car2.setTrim(TRIM_CAR_2);
        car2.setYear(YEARS_CAR_2);
        car2.setTco(TCO_CAR_2);

        List<Car> cars = new ArrayList<>();

        cars.add(car1);
        cars.add(car2);

        return cars;

    }

    @Before
    public void setup() {
        when(mockCarRepository.findAll()).thenReturn(createListOfCars());
    }

    @Test
    public void testGetAllCarsFromDB() {
        List<Car> allCarsFromDB = carBusinessService.getAllCarsFromDB();

        assertEquals(allCarsFromDB.size(), 2);
        assertEquals(allCarsFromDB.get(0).getTco(), TCO_CAR_1);
        assertEquals(allCarsFromDB.get(0).getTrim(), TRIM_CAR_1);
        assertEquals(allCarsFromDB.get(0).getModel(), MODELS_CAR_1);
        assertEquals(allCarsFromDB.get(0).getYear(), YEARS_CAR_1);

    }

}