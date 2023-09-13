package utils;

public class Divider {
    public static void printDivider() {
        System.out.println("--------------------");
    }

    public static void printDivider(int length) {
        for (int i = 0; i < length; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}
