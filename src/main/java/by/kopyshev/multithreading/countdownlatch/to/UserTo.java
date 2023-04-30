package by.kopyshev.multithreading.countdownlatch.to;

import by.kopyshev.multithreading.countdownlatch.model.User;

import java.util.List;

public record UserTo(String name, List<MealTo> meals) {
    public UserTo(User user, List<MealTo> mealTos) {
        this(user.name(), mealTos);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        meals.forEach(mealTo ->
                builder.append("        MealTo{ ")
                .append(mealTo.date()).append(", ")
                .append(mealTo.name()).append(", ")
                .append(mealTo.calories()).append(", ")
                .append(mealTo.excess()).append(" }")
                .append("\n"));

        return "UserTo {\n" +
                "    name: " + name + "\n" +
                "    meals:\n" +
                builder +
                "}";
    }
}
