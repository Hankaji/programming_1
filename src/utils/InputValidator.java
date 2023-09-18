package utils;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

public class InputValidator {
    public static int validateInt(Function<Integer, Boolean> function) {
        Scanner scanner = new Scanner(System.in);
        int attempts = 1;
        while (attempts <= 3) {
            try {
                int userInput = scanner.nextInt();

                if (function.apply(userInput)) return userInput;
                System.out.println("Invalid input, please try again");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Input error");
            }
            attempts++;
        }
        System.out.println("Too many attempts. Exiting program.");
        return -1;
    }

    public static double validateDouble(Function<Double, Boolean> function) {
        Scanner scanner = new Scanner(System.in);
        int attempts = 1;
        while (attempts <= 3) {
            try {
                double userInput = scanner.nextDouble();

                if (function.apply(userInput)) return userInput;
                System.out.println("Invalid input, please try again");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid double.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Input error");
            }
            attempts++;
        }
        System.out.println("Too many attempts. Exiting program.");
        return -1;
    }

    public static String validateString(Function<String, Boolean> function) {
        Scanner scanner = new Scanner(System.in);
        int attempts = 1;
        while (attempts <= 3) {
            try {
                String userInput = scanner.nextLine();

                if (function.apply(userInput)) return userInput;
                System.out.println("Invalid input, please try again");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid string.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Input error");
            }
            attempts++;
        }
        System.out.println("Too many attempts. Exiting program.");
        return null;
    }

}
