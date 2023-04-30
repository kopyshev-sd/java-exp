package by.kopyshev.multithreading.countdownlatch.model;

import java.util.List;

public record User(String name, List<Meal> meals) {
}
