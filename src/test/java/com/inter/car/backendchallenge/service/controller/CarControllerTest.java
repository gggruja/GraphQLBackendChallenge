package com.inter.car.backendchallenge.service.controller;

import com.inter.car.backendchallenge.service.GraphQLService;
import com.inter.car.backendchallenge.service.business.service.CarBusinessService;
import com.inter.car.backendchallenge.service.business.service.CarQueryBusinessService;
import com.inter.car.backendchallenge.service.business.service.impl.CarBusinessServiceImpl;
import com.inter.car.backendchallenge.service.business.service.impl.CarQueryBusinessServiceImpl;
import com.inter.car.backendchallenge.service.configuration.CarQueryConfiguration;
import com.inter.car.backendchallenge.service.configuration.LoggingConfiguration;
import com.inter.car.backendchallenge.service.datafetcher.AllCarsByMakeFilterDataFetcher;
import com.inter.car.backendchallenge.service.datafetcher.AllCarsByModelFilterDataFetcher;
import com.inter.car.backendchallenge.service.datafetcher.AllCarsFromDBDataFetcher;
import com.inter.car.backendchallenge.service.repository.CarRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { CarControllerTest.TestConfig.class })
@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Configuration
    @Import(LoggingConfiguration.class)
    public static class TestConfig {

        @Bean
        public CarController carController() {
            return new CarController();
        }

        @Bean
        public GraphQLService graphQLService() {
            return new GraphQLService();
        }

        @Bean
        public AllCarsFromDBDataFetcher allCarsFromDBDataFetcher() {
            return new AllCarsFromDBDataFetcher();
        }

        @Bean
        public AllCarsByModelFilterDataFetcher allCarsByModelFilterDataFetcher() {
            return new AllCarsByModelFilterDataFetcher();
        }

        @Bean
        public AllCarsByMakeFilterDataFetcher allCarsByMakeFilterDataFetcher() {
            return new AllCarsByMakeFilterDataFetcher();
        }

        @Bean
        public CarBusinessService carBusinessService() {
            return new CarBusinessServiceImpl();
        }

        @Bean
        public CarQueryBusinessService carQueryBusinessService() {
            return new CarQueryBusinessServiceImpl();
        }

    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GraphQLService graphQLService;

    @Autowired
    private CarController carController;

    @Autowired
    private AllCarsFromDBDataFetcher allCarsFromDBDataFetcher;

    @Autowired
    private AllCarsByModelFilterDataFetcher allCarsByModelFilterDataFetcher;

    @Autowired
    private AllCarsByMakeFilterDataFetcher allCarsByMakeFilterDataFetcher;

    @MockBean
    private CarBusinessService carBusinessService;

    @MockBean
    private CarQueryBusinessService carQueryBusinessService;

    @MockBean
    private CarRepository carRepository;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private CarQueryConfiguration carQueryConfiguration;

    @Test
    public void getCarsControllerTest() throws Exception {
        // Given
        String query = "{" +
                "cars {" +
                "  make" +
                "  model" +
                "}}";
        // When
        ResponseEntity<Object> cars = carController.getCars(query);

        // Then
        assertEquals(cars.getStatusCode().value(), HttpStatus.OK.value());

    }

    @Test
    public void getCarsControllerTestWithMock() throws Exception {
        // Given
        String query = "{" +
                "cars {" +
                "  make" +
                "  model" +
                "}}";

        // test call
        mockMvc.perform(post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(query))
                .andExpect(status().isOk());

        // validation
        verify(carBusinessService, times(1)).getAllCarsFromDB();
        verify(carQueryBusinessService, times(0)).getCarsTCObyMake(any(String.class));
        verify(carQueryBusinessService, times(0)).getCarsTCOByModel(any(String.class), any(String.class));
    }

    @Test
    public void getAllCarsByMakeControllerTestWithMock() throws Exception {
        // Given
        String query = "{\n"
                + "      carsByMakeFilter(cmd:\"getMakes\") {\n"
                + "          make\n"
                + "          model\n"
                + "          tco\n"
                + "      }\n"
                + "    }";

        // test call
        mockMvc.perform(post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(query))
                .andExpect(status().isOk());

        // validation
        verify(carQueryBusinessService, times(1)).getCarsTCObyMake(any(String.class));
        verify(carQueryBusinessService, times(0)).getCarsTCOByModel(any(String.class), any(String.class));
        verify(carBusinessService, times(0)).getAllCarsFromDB();
    }

    @Test
    public void getAllCarsByModelControllerTestWithMock() throws Exception {
        // Given
        String query = "{\n"
                + "      carsByModelFilter(cmd:\"getMakes\", make:\"BMW\") {\n"
                + "          make\n"
                + "          model\n"
                + "          tco\n"
                + "      }\n"
                + "    }";

        // test call
        mockMvc.perform(post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(query))
                .andExpect(status().isOk());

        // validation
        verify(carQueryBusinessService, times(0)).getCarsTCObyMake(any(String.class));
        verify(carQueryBusinessService, times(1)).getCarsTCOByModel(any(String.class), any(String.class));
        verify(carBusinessService, times(0)).getAllCarsFromDB();
    }

    @Test
    public void wrongQueryControllerTest() throws Exception {
        // Given
        String query = "{\n"
                + "      wrongQuery(cmd:\"getMakes\") {\n"
                + "          make\n"
                + "          model\n"
                + "          tco\n"
                + "      }\n"
                + "    }";

        // test call
        mockMvc.perform(post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(query))
                .andExpect(status().isOk());

        // validation
        verify(carQueryBusinessService, times(0)).getCarsTCObyMake(any(String.class));
        verify(carQueryBusinessService, times(0)).getCarsTCOByModel(any(String.class), any(String.class));
        verify(carBusinessService, times(0)).getAllCarsFromDB();
    }

    @Test
    public void noQueryControllerTestBadRequest() throws Exception {
        // test call
        mockMvc.perform(post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());

        // validation
        verify(carQueryBusinessService, times(0)).getCarsTCObyMake(any(String.class));
        verify(carQueryBusinessService, times(0)).getCarsTCOByModel(any(String.class), any(String.class));
        verify(carBusinessService, times(0)).getAllCarsFromDB();
    }

}