package ui;

import java.util.Scanner;

/**
 * Utility class for collecting and validating user input. 
 * Provides methods to read and parse different types of input from the user (integer, string, and double).
 * If the input is invalid, appropriate handling is implemented (e.g., returning -1 for invalid integers).
 */
public class ImportUtils {

    /**
     * Reads an integer input from the user. 
     * If the input is not a valid integer, returns -1 to indicate an invalid input.
     * 
     * @param scanner The scanner object used to read user input.
     * @return The integer input provided by the user, or -1 if the input is invalid.
     */
    public static int getUserChoice(Scanner scanner) {
        try {
            // Attempt to parse the user input as an integer
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            // Return -1 in case of invalid input
            return -1; // Invalid input
        }
    }

    /**
     * Reads a string input from the user.
     * 
     * @param scanner The scanner object used to read user input.
     * @return The string input provided by the user.
     */
    public static String getUserChoiceStr(Scanner scanner) {
        // Return the user input as a string
        return (scanner.nextLine());
    }

    /**
     * Reads a double input from the user.
     * If the input is not a valid double, it throws a runtime exception (e.g., NumberFormatException).
     * 
     * @param scanner The scanner object used to read user input.
     * @return The double input provided by the user.
     */
    public static double getUserChoiceDouble(Scanner scanner) {
        try {
            // Parse and return the input as a double
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            // Return -1 in case of invalid input
            return -1; // Invalid input
        }
    }
}
