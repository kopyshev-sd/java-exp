package by.kopyshev.multithreading.countdownlatch;

import by.kopyshev.multithreading.countdownlatch.model.Meal;
import by.kopyshev.multithreading.countdownlatch.model.User;
import by.kopyshev.multithreading.countdownlatch.to.MealTo;
import by.kopyshev.multithreading.countdownlatch.to.UserTo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static by.kopyshev.multithreading.countdownlatch.TestData.MAX_MEALS;
import static by.kopyshev.multithreading.countdownlatch.TestData.generateUsers;
import static by.kopyshev.util.ThreadUtil.awaitTermination;
import static by.kopyshev.util.ThreadUtil.safetyAwait;
import static java.util.Comparator.comparing;
import static java.util.Objects.isNull;

public class CountDownLatchTest {
    private static final int USER_COUNT = 5;
    private static final int EXCESS = 600;
    private static final int THREAD_POOL = USER_COUNT * MAX_MEALS + 1;
    private static final int TIMEOUT = 10;

    public static void main(String[] args) {
        List<User> users = generateUsers(USER_COUNT);
        List<UserTo> userTos = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(THREAD_POOL);

        for (User user : users) {
            CountDownLatch userLatch = new CountDownLatch(user.meals().size());
            Map<LocalDate, Integer> caloriesDaily = new ConcurrentHashMap<>();
            List<MealTo> mealTos = new ArrayList<>();

            for (Meal meal : user.meals()) {
                service.submit(() -> {
                    caloriesDaily.compute(meal.date(),
                            (date, calories) -> isNull(calories)
                                    ? meal.calories()
                                    : calories + meal.calories());
                    userLatch.countDown();
                    safetyAwait(userLatch);

                    mealTos.add(new MealTo(meal, caloriesDaily.get(meal.date()) > EXCESS));
                });
            }
            userTos.add(new UserTo(user, mealTos));
        }
        service.shutdown();
        awaitTermination(service, TIMEOUT, TimeUnit.SECONDS);
        printUserTos(userTos);
    }

    private static void printUserTos(List<UserTo> userTos) {
        for (UserTo userTo : userTos) {
            userTo.meals().sort(comparing(MealTo::date));
            System.out.println(userTo + "\n");
        }
    }
}
