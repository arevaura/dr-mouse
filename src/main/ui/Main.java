package ui;

/**
 * Represents the main class that runs the program.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.print("\033[H\033[2J"); // clears terminal
        System.out.println("Welcome, Dr. Mouse says hello!\nSQUEAK!"); // welcome message
        System.out.println("\nPlease enter [any key] to embark on your journalling journey today:");
        System.in.read(); // waits for user to enter any key
        new UserHandler(); // starts the main user program
    }
}
