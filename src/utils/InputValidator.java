package utils;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

public class InputValidator {
    public static int validateInt(Function<Integer, Boolean> function) {
        return validateInt(function, "", "Input error");
    }

    public static int validateInt(Function<Integer, Boolean> function, String question) {
        return validateInt(function, question, "Input error");
    }

    public static int validateInt(Function<Integer, Boolean> function, String question, String errorMessage) {
        Scanner scanner = new Scanner(System.in);
        int attempts = 1;
        while (attempts <= 3) {
            try {
                System.out.println(question);
                int userInput = scanner.nextInt();

                if (userInput == -1) return -1;
                if (function.apply(userInput)) return userInput;
                System.out.println("Invalid input, please try again");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println(errorMessage);
            }
            attempts++;
        }
        System.out.println("Too many attempts. Exiting program.");
        return -1;
    }

    public static double validateDouble(Function<Double, Boolean> function) {
        return validateDouble(function, "", "Input error");
    }

    public static double validateDouble(Function<Double, Boolean> function, String question) {
        return validateDouble(function, question, "Input error");
    }

    public static double validateDouble(Function<Double, Boolean> function, String question, String errorMessage) {
        Scanner scanner = new Scanner(System.in);
        int attempts = 1;
        while (attempts <= 3) {
            try {
                System.out.println(question);
                double userInput = scanner.nextDouble();

                if (userInput == -1) return -1;
                if (function.apply(userInput)) return userInput;
                System.out.println("Invalid input, please try again");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid double.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println(errorMessage);
            }
            attempts++;
        }
        System.out.println("Too many attempts. Exiting program.");
        return -1;
    }

    public static String validateString(Function<String, Boolean> function) {
        return validateString(function, "", "Input error");
    }

    public static String validateString(Function<String, Boolean> function, String question) {
        return validateString(function, question, "Input error");
    }

    public static String validateString(Function<String, Boolean> function, String question, String errorMessage) {
        Scanner scanner = new Scanner(System.in);
        int attempts = 1;
        while (attempts <= 3) {
            try {
                System.out.println(question);
                String userInput = scanner.nextLine();

                if (userInput.equals("-1")) return "-1";
                if (function.apply(userInput)) return userInput;
                System.out.println("Invalid input, please try again");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid string.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println(errorMessage);
            }
            attempts++;
        }
        System.out.println("Too many attempts. Exiting program.");
        return null;
    }

}
