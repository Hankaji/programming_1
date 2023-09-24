package utils;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Function;

public class InputValidator {
    public static int validateInt(String question) {
        return validateInt((v) -> true, question, "Invalid input, please try again.");
    }
    public static int validateInt(Function<Integer, Boolean> function) {
        return validateInt(function, "", "Invalid input, please try again.");
    }

    public static int validateInt(Function<Integer, Boolean> function, String question) {
        return validateInt(function, question, "Invalid input, please try again.");
    }

    public static Date validateDate(String question) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(question);
                String userInput = scanner.nextLine();

                if (userInput.equals("-1")) return null;
                Date date = (new SimpleDateFormat("dd/MM/yyyy")).parse(userInput);
                if (date != null) return date;
                System.out.println("Invalid input. Please enter a valid date.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid date.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error occurred, please try again.");
            }
        }
    }

    public static int validateInt(Function<Integer, Boolean> function, String question, String errorMessage) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(question);
                int userInput = scanner.nextInt();

                if (userInput == -1) return -1;
                if (function.apply(userInput)) return userInput;
                System.out.println(errorMessage);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error occurred, please try again.");
            }
        }
    }

    public static double validateDouble(String question) {
        return validateDouble((v) -> true, question, "Invalid input, please try again.");
    }

    public static double validateDouble(Function<Double, Boolean> function) {
        return validateDouble(function, "", "Invalid input, please try again.");
    }

    public static double validateDouble(Function<Double, Boolean> function, String question) {
        return validateDouble(function, question, "Invalid input, please try again.");
    }

    public static double validateDouble(Function<Double, Boolean> function, String question, String errorMessage) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(question);
                double userInput = scanner.nextDouble();

                if (userInput == -1) return -1;
                if (function.apply(userInput)) return userInput;
                System.out.println(errorMessage);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid double.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error occurred, please try again.");
            }
        }
    }

    public static String validateString(String question) {
        return validateString((v) -> true, question, "Invalid input, please try again.");
    }

    public static String validateString(Function<String, Boolean> function) {
        return validateString(function, "", "Invalid input, please try again.");
    }

    public static String validateString(Function<String, Boolean> function, String question) {
        return validateString(function, question, "Invalid input, please try again.");
    }

    public static String validateString(Function<String, Boolean> function, String question, String errorMessage) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(question);
                String userInput = scanner.nextLine();

                if (userInput.equals("-1")) return "-1";
                if (function.apply(userInput)) return userInput;
                System.out.println(errorMessage);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid string.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Error occurred, please try again.");
            }
        }
    }

    public static boolean validateBoolean(String question) {
        String answer = validateString((v) -> Arrays.asList("true", "false", "t", "f", "yes", "no", "y", "n").contains(v.toLowerCase()),
                question,
                "Invalid input. Please enter a valid boolean.");
        return !answer.equals("-1") && Arrays.asList("true", "t", "yes", "y").contains(answer.toLowerCase());
    }

}
