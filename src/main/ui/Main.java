package ui;

import java.io.FileNotFoundException;

import ui.gui.JournalUI;

/**
 * Represents the main class that runs the program.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        new JournalUI();

        Terminal.clearScreen();
        ;
        System.out.println("Welcome, Dr. Mouse says hello!\n~squeak =.="); // welcome message
        System.out.println("\nPlease enter [any key] to embark on your journalling journey today:");
        System.in.read(); // waits for user to enter any key

        try {
            new UserHandler(); // starts the main user program
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
