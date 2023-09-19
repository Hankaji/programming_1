package utils;

import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

public class InputValidator {
    public static int validateInt(Function<Integer, Boolean> function) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                int userInput = scanner.nextInt();

                if (function.apply(userInput)) return userInput;
                System.out.println("Invalid input, please try again");
            } catch (Exception e) {
                System.out.println("Input error");
            }
        }
    }

    public static double validateDouble(Function<Double, Boolean> function) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                double userInput = scanner.nextDouble();

                if (function.apply(userInput)) return userInput;
                System.out.println("Invalid input, please try again");
            } catch (Exception e) {
                System.out.println("Input error");
            }
        }
    }

    public static String validateString(Function<String, Boolean> function) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                String userInput = scanner.nextLine();

                if (function.apply(userInput)) return userInput;
                System.out.println("Invalid input, please try again");
            } catch (Exception e) {
                System.out.println("Input error");
            }
        }
    }

}
