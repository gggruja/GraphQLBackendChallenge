package com.inter.car.backendchallenge.service.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Table(name = "car")
@Entity
public class Car {

    String make;
    String model;
    @Id
    String trim;
    int year;
    int tco;

    public Car(String make, String model, String trim, int year, int tco) {
        this.make = make;
        this.model = model;
        this.trim = trim;
        this.year = year;
        this.tco = tco;
    }

    public Car() {

    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTco() {
        return tco;
    }

    public void setTco(int tco) {
        this.tco = tco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Car car = (Car) o;
        return year == car.year &&
                tco == car.tco &&
                Objects.equals(make, car.make) &&
                Objects.equals(model, car.model) &&
                Objects.equals(trim, car.trim);
    }

    @Override
    public int hashCode() {
        return Objects.hash(make, model, trim, year, tco);
    }

    @Override public String toString() {
        return "Car{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", trim='" + trim + '\'' +
                ", year=" + year +
                ", tco=" + tco +
                '}';
    }
}
