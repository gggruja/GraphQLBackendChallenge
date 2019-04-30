package com.inter.car.backendchallenge.service.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CarQueryConfiguration.class)
@ConfigurationProperties(prefix = "backend.challenge.carqueryapi")
public class CarQueryConfiguration {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override public String toString() {
        return "CarQueryConfiguration{" +
                "url='" + url + '\'' +
                '}';
    }
}
