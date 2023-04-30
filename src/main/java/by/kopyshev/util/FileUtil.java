package by.kopyshev.util;

import by.kopyshev.multithreading.countdownlatch.TestData;

import java.io.*;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class FileUtil {
    public static void readFile(String path, List<String> list) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(requireNonNull(
                    Class.forName(TestData.class.getName())
                            .getClassLoader()
                            .getResourceAsStream(path))));

            while (reader.ready()) {
                list.add(reader.readLine());
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void append(String path, String string) {
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(string);
            writer.flush();
        } catch (IOException e) {
            System.out.println("WASTED xD");
            e.printStackTrace();
        }
    }
}
