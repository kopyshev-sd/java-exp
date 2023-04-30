package by.kopyshev.multithreading.countdownlatch;

import by.kopyshev.multithreading.countdownlatch.model.Meal;
import by.kopyshev.multithreading.countdownlatch.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static by.kopyshev.util.FileUtil.readFile;

public class TestData {
    public static final int MAX_LAST_DAYS = 5;
    public static final int MIN_MEALS = 3;
    public static final int MAX_MEALS = 7;
    public static final String FIRST_NAMES_TXT = "countdownlatch/first names.txt";
    public static final String LAST_NAMES_TXT = "countdownlatch/last names.txt";
    public static final String MEALS = "countdownlatch/meals.txt";

    private static final Random random = new Random();
    private static final List<String> firstNames = new ArrayList<>();
    private static final List<String> lastNames = new ArrayList<>();
    private static final List<String> meals = new ArrayList<>();

    static {
        readFile(FIRST_NAMES_TXT, firstNames);
        readFile(LAST_NAMES_TXT, lastNames);
        readFile(MEALS, meals);
    }

    public static List<User> generateUsers(int count) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            users.add(generateUser());
        }
        return users;
    }

    private static User generateUser() {
        User user = new User(getRandomName(), new ArrayList<>());
        for (int i = 0; i < random.nextInt(MAX_MEALS - MIN_MEALS) + MIN_MEALS; i++) {
            user.meals().add(generateMeal());
        }
        return user;
    }

    private static Meal generateMeal() {
        LocalDate date = LocalDate.now()
                .minusDays(random.nextInt(MAX_LAST_DAYS));

        String[] meal = meals.get(random.nextInt(meals.size())).split("\\|");
        return new Meal(date, meal[0], Integer.parseInt(meal[1].trim()));
    }

    private static String getRandomName() {
        String firstName = firstNames.get(random.nextInt(firstNames.size()));
        String lastName = lastNames.get(random.nextInt(lastNames.size()));
        return firstName + " " + lastName;
    }
}