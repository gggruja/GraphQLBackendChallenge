package com.inter.car.backendchallenge.service.repository;

import com.inter.car.backendchallenge.service.entity.Car;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarRepositoryTest {

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

    @Autowired
    CarRepository carRepository;

    public Car createCarObject() {
        Car car = new Car();

        car.setMake(MAKES_CAR_1);
        car.setModel(MODELS_CAR_1);
        car.setTrim(TRIM_CAR_1);
        car.setYear(YEARS_CAR_1);
        car.setTco(TCO_CAR_1);

        return car;

    }

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
    public void deleteAllBefore() {
        carRepository.deleteAll();
    }

    @Test
    public void whenFindingCarByTrim_thenCorrect() {

        carRepository.save(createCarObject());

        Car oneByTrim = carRepository.findOneByTrim(TRIM_CAR_1);

        assertEquals(oneByTrim.getMake(), MAKES_CAR_1);
        assertEquals(oneByTrim.getTco(), TCO_CAR_1);
        assertEquals(oneByTrim.getTrim(), TRIM_CAR_1);
        assertEquals(oneByTrim.getModel(), MODELS_CAR_1);
        assertEquals(oneByTrim.getYear(), YEARS_CAR_1);

    }

    @Test
    public void whenFindingAllCars_thenCorrect() {

        carRepository.saveAll(createListOfCars());

        List<Car> all = carRepository.findAll();

        assertEquals(all.size(), 2);

    }

    @Test
    public void whenFindingAllCarsByTrim_thenCorrect() {

        carRepository.saveAll(createListOfCars());

        List<Car> carsByTrim = carRepository.findAllByMake(MAKES_CAR_1);

        assertEquals(carsByTrim.size(), 2);

    }

    @Test
    public void whenFindingAllCarsTryToSaveExistingOne_thenCorrect() {

        carRepository.saveAll(createListOfCars());
        carRepository.save(createCarObject());

        List<Car> carsByTrim = carRepository.findAll();

        assertEquals(carsByTrim.size(), 2);

    }

    @Test
    public void whenFindingAllCarsUpdateExistingOne_thenCorrect() {

        final String UPDATED = "GGG";

        carRepository.saveAll(createListOfCars());
        Car carObjectForUpdate = createCarObject();
        carObjectForUpdate.setMake(UPDATED);
        carRepository.save(carObjectForUpdate);

        List<Car> carsByTrim = carRepository.findAll();

        assertEquals(carsByTrim.size(), 2);

        List<Car> allByMake = carRepository.findAllByMake(UPDATED);
        assertEquals(allByMake.size(), 1);

        assertEquals(allByMake.get(0).getMake(), UPDATED);
        assertEquals(allByMake.get(0).getTco(), TCO_CAR_1);
        assertEquals(allByMake.get(0).getTrim(), TRIM_CAR_1);
        assertEquals(allByMake.get(0).getModel(), MODELS_CAR_1);
        assertEquals(allByMake.get(0).getYear(), YEARS_CAR_1);

    }

    @After
    public void deleteAllAfter() {
        carRepository.deleteAll();
    }

}