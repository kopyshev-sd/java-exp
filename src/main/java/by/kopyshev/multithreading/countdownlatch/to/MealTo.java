package by.kopyshev.multithreading.countdownlatch.to;

import by.kopyshev.multithreading.countdownlatch.model.Meal;

import java.time.LocalDate;

public record MealTo(LocalDate date, String name, int calories, boolean excess) {
    public MealTo(Meal meal, boolean excess) {
        this(meal.date(), meal.name(), meal.calories(), excess);
    }

    @Override
    public String toString() {
        return "MealTo{ " + date +
                ", " + name +
                ", " + calories +
                ", excess: " + excess
                + " }";
    }
}
