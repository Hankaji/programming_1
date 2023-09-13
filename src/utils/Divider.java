package utils;

import java.io.Serializable;

public class Divider implements Serializable {
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
