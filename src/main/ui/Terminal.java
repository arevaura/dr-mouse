package ui;

import java.io.IOException;

/**
 * This class is to store helper methods useful to all src modules.
 * Therefore, there is no Terminal constructor defined in the code.
 */
public class Terminal {
    /*
     * EFFECTS: clears the terminal screen;
     */
    public static void clearScreen() {
        // return;
        System.out.print("\033[H\033[2J");
    }

    /*
     * EFFECTS: waits for user to enter any key to continue;
     */
    public static void waitForKey() {
        try {
            System.in.read();
        } catch (IOException e) { // if failure occurs in reading input
            System.out.println("\n\nSqueak...Something went wrong :(");
        }
    }
}
