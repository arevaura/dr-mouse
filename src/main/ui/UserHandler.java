package ui;

import java.io.IOException;
import java.util.Scanner;

import model.*;

/**
 * Represents a user handler that manages the user's interaction with the journal.
 */
public class UserHandler {
    private Journal journal = new Journal();
    private Scanner input = new Scanner(System.in);
    private int invalid = 1111111111;
    private int exit = -1;
    private int view = 0;
    private int add = 1;
    private int remove = 2;

    // ==========--CONSTRUCTOR--==========
    /**
     * Constructs a new UserHandler object and prompts the user to choose an action.
     * Then executes the corresponding action according to the appropriate method.
     */
    public UserHandler() {
        while (true) {
            runMenu();
            System.out.println("Returning to the main menu...");
            System.out.println("/n====================/n");
        }
    }

    // ==========--ACTIONS--==========
    /*
     * EFFECTS: runs the main menu by calling the chooseAction method
     * -------- and then executes the corresponding action call;
     */
    private void runMenu() {
        if (chooseAction() == exit) {
            executeExit();
        } else if (chooseAction() == view) {
            executeView();
        } else if (chooseAction() == add) {
            executeAdd();
        } else if (chooseAction() == remove) {
            executeRemove();
        } else {
            System.out.println("\nSQUEEEAK!...Invalid input. Please try again!");
        }
        System.out.println("\nEnter [any key] to return to the main menu.");
        try {
            System.in.read();
        } catch (IOException e) { // if failure occurs in reading input
            System.out.println("\n\nSqueak...Something went wrong :(");
        }
    }

    /*
     * EFFECTS: executes the exit action by printing a goodbye message;
     */
    private void executeExit() {
        clearScreen();
        System.out.println("Dr. Mouse says goodbye and have a great day!\nSqueak!");
        System.exit(0);
    }

    /*
     * EFFECTS: executes the view action by printing all entries in the journal;
     */
    private void executeView() {
        clearScreen();
        System.out.println("Here are all your log entries:");
        printEntries();
    }

    /*
     * MODIFIES: entries ArrayList in journal
     * EFFECTS: executes the add action by prompting the user to create a new entry;
     */
    private void executeAdd() {
        clearScreen();
        System.out.println("Please type your new log entry:");
        String text = input.nextLine();
        Entry entry = new Entry(text);
        journal.addEntry(entry);
        System.out.println("\nAdding your new log entry...");
        System.out.println("Your new log entry has been added!");
    }
    
    /*
     * MODIFIES: entries ArrayList in journal
     * EFFECTS: executes the remove action by prompting the user to select an entry to remove;
     */
    private void executeRemove() {
        clearScreen();
        Entry entry = selectEntry();
        journal.removeEntry(entry);
        System.out.println("\nRemoving your selected log entry...");
        System.out.println("Your selected log entry has been removed!");
    }

    // ==========--HELPER-METHODS--==========
    /*
     * EFFECTS: clears the terminal screen;
     */
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
    }

    /*
     * EFFECTS: prompts user to choose action and returns corresponding integer;
     * -------- see declared fields for integer values;
     */
    private int chooseAction() {
        clearScreen();
        System.out.println("\nWhat would you like to do?");
        System.out.println("Please type 'exit' to close this journal.");
        if (journal.getNumEntries() != 0) {
            System.out.println("Please type 'view' to view your log entries.");
        }
        System.out.println("Please type 'add' to create a new log entry.");
        if (journal.getNumEntries() != 0) {
            System.out.println("Please type 'remove' to delete a log entry.");
        }

        String selection = input.nextLine().toLowerCase();
        System.out.println("\nYou have selected to:\n" + selection.toUpperCase());
        System.out.println("\nProcessing your selection...\nPlease enter [any key] to continue.");
        if (selection.equals("exit")) {
            return exit;
        } else if (selection.equals("view")) {
            return view;
        } else if (selection.equals("add")) {
            return add;
        } else if (selection.equals("remove")) {
            return remove;
        } else {
            System.out.println("\nSqueakers! That wasn't one of the options!");
            return invalid;
        }
    }

    /*
     * EFFECTS: prompts user to select an entry and returns the selected entry;
     */
    private Entry selectEntry() {
        System.out.println("\nPlease select the entry you would like to remove:");
        for (int i = 0; i < journal.getNumEntries(); i++) {
            System.out.println(i + 1 + ". " + journal.getEntries().get(i).getContent());
        }
        int selection = input.nextInt();
        Entry entry = journal.getEntries().get(selection - 1);
        System.out.println("\nSelecting your log entry now...");
        System.out.println("Success!\nYou have selected the following entry:");
        System.out.println(entry.getContent());
        return entry;
    }

    /*
     * EFFECTS: prints all entries in the journal;
     */
    private void printEntries() {
        for (Entry entry : journal.getEntries()) {
            System.out.println(entry.getContent());
            System.out.println("----------\n");
        }
    }
}