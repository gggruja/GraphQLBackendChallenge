package com.inter.car.backendchallenge.service.business.service.impl;

import com.inter.car.backendchallenge.service.AbstractCarServiceTest;
import com.inter.car.backendchallenge.service.configuration.CarQueryConfiguration;
import com.inter.car.backendchallenge.service.configuration.LoggingConfiguration;
import com.inter.car.backendchallenge.service.entity.Car;
import com.inter.car.backendchallenge.service.repository.CarRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { CarQueryBusinessServiceImpl.class })
@Import({ LoggingConfiguration.class })
public class CarQueryBusinessServiceImplTest extends AbstractCarServiceTest {

    public static final String COMMAND_GET_MAKES = "getMakes";
    public static final String COMMAND_GET_MODELS = "getModels";

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

    @MockBean
    RestTemplate mockRestTemplate;

    @MockBean
    CarQueryConfiguration carQueryConfiguration;

    @Autowired
    CarQueryBusinessServiceImpl carQueryBusinessService;

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

        when(mockCarRepository.findAllByMake(any(String.class))).thenReturn(createListOfCars());
        when(mockCarRepository.findAllByMakeAndModel(any(String.class), any(String.class)))
                .thenReturn(createListOfCars());
        when(mockRestTemplate.exchange(contains(COMMAND_GET_MAKES), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenReturn(new ResponseEntity<>(resultMakes, HttpStatus.OK));
        when(mockRestTemplate.exchange(contains(COMMAND_GET_MODELS), eq(HttpMethod.GET), any(), eq(String.class)))
                .thenReturn(new ResponseEntity<>(resultModel, HttpStatus.OK));

    }

    @Test
    public void testGetCarsTCObyMake() {

        List<Car> carsTCObyMake = carQueryBusinessService.getCarsTCObyMake(COMMAND_GET_MAKES);

        assertEquals(carsTCObyMake.size(), 2);
        assertEquals(carsTCObyMake.get(0).getTco(), TCO_CAR_1);
        assertEquals(carsTCObyMake.get(0).getTrim(), TRIM_CAR_1);
        assertEquals(carsTCObyMake.get(0).getModel(), MODELS_CAR_1);
        assertEquals(carsTCObyMake.get(0).getYear(), YEARS_CAR_1);

    }

    @Test
    public void testGetCarsTCOByModel() {

        List<Car> carsTCObyMake = carQueryBusinessService.getCarsTCOByModel(COMMAND_GET_MODELS, MAKES_CAR_1);

        assertEquals(carsTCObyMake.size(), 2);
        assertEquals(carsTCObyMake.get(0).getTco(), TCO_CAR_1);
        assertEquals(carsTCObyMake.get(0).getTrim(), TRIM_CAR_1);
        assertEquals(carsTCObyMake.get(0).getModel(), MODELS_CAR_1);
        assertEquals(carsTCObyMake.get(0).getYear(), YEARS_CAR_1);

    }

}