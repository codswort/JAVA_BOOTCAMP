package edu.school21.app;

import edu.school21.logic.Logic;
import edu.school21.models.Car;
import edu.school21.models.User;

public class Main {
    public static void main(String[] args) {
        Logic logic = new Logic(User.class, Car.class, java.util.Arrays.class);
        logic.start();
    }
}