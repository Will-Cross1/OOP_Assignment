package ui;

import java.util.Scanner;

public class ImportUtils {

    public static int getUserChoice(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Invalid input
        }
    }

    public static String getUserChoiceStr(Scanner scanner) {
        return (scanner.nextLine());
    }
}
