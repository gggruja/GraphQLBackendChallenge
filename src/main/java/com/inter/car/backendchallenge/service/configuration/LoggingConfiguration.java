package com.inter.car.backendchallenge.service.configuration;

import com.inter.car.backendchallenge.service.logging.annotation.LogInjector;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = LogInjector.class)
public class LoggingConfiguration {

}
