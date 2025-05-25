package edu.school21.models;

import java.util.StringJoiner;
public class Car {
    private String model;
    private double mileAge;
    private int year;

    public Car() {
        this.model = "Default model";
        this.mileAge = 0.0;
        this.year = 2000;
    }

    public Car(String model, double mileAge, int year) {
        this.model = model;
        this.mileAge = mileAge;
        this.year = year;
    }

    public double growMileAge(double newMileAge) {
        this.mileAge += newMileAge;
        return mileAge;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("brand='" + model + "'")
                .add("mileAge='" + mileAge + "'")
                .add("year=" + year)
                .toString();
    }
}
