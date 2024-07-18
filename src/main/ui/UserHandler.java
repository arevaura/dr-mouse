package ui;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import model.*;

/**
 * Represents a handler that manages the user's interaction with the journal.
 * Users can view, add, remove, and edit entries in the journal.
 * All feedback messages and UI are displayed in the terminal.
 */
public class UserHandler {
    private Journal journal = new Journal();
    private Scanner input = new Scanner(System.in);
    private static final int INVALID = 1111111111;
    private static final int EXIT = -1;
    private static final int VIEW = 0;
    private static final int ADD = 1;
    private static final int REMOVE = 2;
    private static final int EDIT = 3;
    // private static final int FILTER = 4;
    private static final int TOTAL = 5;

    // ==========--CONSTRUCTOR--==========
    /**
     * Constructs a new UserHandler object and prompts the user to choose an action.
     * A menu of options is given for the user's reference.
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
     * -------- which will determine the action to execute;
     */
    private void runMenu() {
        if (chooseAction() == EXIT) {
            executeExit();
        } else if (chooseAction() == VIEW) {
            executeView();
        } else if (chooseAction() == ADD) {
            executeAdd();
        } else if (chooseAction() == REMOVE) {
            executeRemove();
        } else if (chooseAction() == EDIT) {
            executeEdit();
            // } else if (chooseAction() == FILTER) {
            // executeFilter();
        } else if (chooseAction() == TOTAL) {
            executeTotal();
        } else if (chooseAction() == INVALID) {
            System.out.println("\nSQUEEEAK!...Invalid input. Please try again!");
        }
        System.out.println("\nEnter [any key] to return to the main menu.");
        waitForKey();
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
        System.out.println("Here are all your log entries:\n====================\n");
        printEntries(journal.getEntries());
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
     * EFFECTS: executes the remove action by prompting the user to select an entry
     * -------- to remove;
     */
    private void executeRemove() {
        clearScreen();
        Entry entry = selectEntry();
        journal.removeEntry(entry);
        System.out.println("\nRemoving your selected log entry...");
        System.out.println("Your selected log entry has been removed!");
    }

    /*
     * MODIFIES: entry object from entries ArrayList in journal
     * EFFECTS: executes the edit action by prompting the user to select an entry to
     * -------- modify;
     */
    private void executeEdit() {
        clearScreen();
        Entry entry = selectEntry();
        System.out.println("\nPlease type 1 to edit the title, 2 to edit the content, or 3 to edit the mood:");
        int action = input.nextInt();
        input.nextLine(); // Consume newline left-over
        if (action == 1) {
            System.out.println("Please enter the new title for your log entry:");
            String title = input.nextLine().toUpperCase();
            entry.setTitle(title);
        } else if (action == 2) {
            System.out.println("Please enter the new text for your log entry:");
            entry.setContent(input.nextLine());
        } else if (action == 3) {
            System.out.println("Please enter your mood for this log entry:");
            entry.setMood(input.nextLine());
        } else {
            System.out.println("\nSqueak! That wasn't one of the edit options!");
            executeEdit();
        }
        System.out.println("\nEditing your selected log entry...");
        System.out.println("Your selected log entry has been edited!");
    }

    // /*
    // * EFFECTS: executes the filter action by prompting the user to enter a date
    // to filter entries;
    // */
    // private void executeFilter() {
    // clearScreen();
    // System.out.println("Please enter 1 to filter by date, 2 to filter by mood, or
    // 3 to filter by keyword:");
    // int action = input.nextInt();
    // // input.nextLine(); // Consume newline left-over
    // String search = null;
    // if (action == 1) {
    // System.out.println("Please enter the date you want to filter by (i.e.
    // \"2021-01-01\"):");
    // search = input.nextLine().toLowerCase();
    // printEntries(journal.filterEntriesByDate(search));
    // } else if (action == 2) {
    // System.out.println("Please enter the mood you want to filter by (i.e.
    // \"happy\"):");
    // search = input.nextLine().toLowerCase();
    // printEntries(journal.filterEntriesByMood(search));
    // } else if (action == 3) {
    // System.out.println("Please enter the keyword you want to filter by (i.e.
    // \"cheese\"):");
    // search = input.nextLine().toLowerCase();
    // printEntries(journal.filterEntriesByKeyword(search));
    // } else {
    // System.out.println("\nSqueaky cheese! That wasn't one of the filter
    // options!");
    // executeFilter();
    // }
    // }

    /*
     * EFFECTS: prints the number of entries in the journal;
     */
    private void executeTotal() {
        clearScreen();
        if (journal.getNumEntries() == 0) {
            System.out.println("You don't have any saved log entries.\nWrite one now!");
        } else if (journal.getNumEntries() == 1) {
            System.out.println("You only have 1 saved log entry. Keep going!");
        } else { 
            System.out.println("\nYou have " + journal.getNumEntries() + " saved log entries. Awesome!");
        }
    }

    // ==========--HELPER-METHODS--==========
    /*
     * EFFECTS: clears the terminal screen;
     */
    private void clearScreen() {
        // return;
        System.out.print("\033[H\033[2J");
    }

    /*
     * EFFECTS: waits for user to enter any key to continue;
     */
    private void waitForKey() {
        try {
            System.in.read();
        } catch (IOException e) { // if failure occurs in reading input
            System.out.println("\n\nSqueak...Something went wrong :(");
        }
    }

    /*
     * EFFECTS: prints the main menu options;
     */
    private void printMenuOptions() {
        System.out.println("\nPlease choose one of the following options (i.e. \"add\"):");
        System.out.println("exit - close this journal");
        if (journal.getNumEntries() != 0) {
            System.out.println("view - view all your saved log entries");
        }
        System.out.println("add - log a new entry");
        if (journal.getNumEntries() != 0) {
            System.out.println("remove - delete a log entry");
            System.out.println("edit - modify a saved log entry");
            // System.out.println("filter - view log entries using filter");
        }
        System.out.println("total - view the number of logged entries");
    }

    /*
     * EFFECTS: prompts user to choose action and returns corresponding integer;
     * -------- see declared fields for corresponding integer values;
     */
    private int chooseAction() {
        clearScreen();
        printMenuOptions();
        String selection = input.nextLine().toLowerCase();
        System.out.println("\nYou have selected to:\n" + selection.toUpperCase());
        // System.out.println("\nProcessing your selection...\nPlease enter [any key] to
        // continue.");
        // waitForKey();
        if (selection.equals("exit")) {
            return EXIT;
        } else if (selection.equals("view")) {
            return VIEW;
        } else if (selection.equals("add")) {
            return ADD;
        } else if (selection.equals("remove")) {
            return REMOVE;
        } else if (selection.equals("edit")) {
            return EDIT;
            // } else if (selection.equals("filter")) {
            // return FILTER;
        } else if (selection.equals("total")) {
            return TOTAL;
        } else {
            System.out.println("\nSqueakers! That wasn't one of the menu options!");
            return INVALID;
        }
    }

    /*
     * EFFECTS: prompts user to select an entry and returns the selected entry;
     */
    private Entry selectEntry() {
        System.out.println("\nPlease enter the number of the entry you want to modify (i.e. \"1\"):");
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
     * EFFECTS: prints all saved entries in the journal;
     */
    private void printEntries(List<Entry> entries) {
        for (Entry entry : journal.getEntries()) {
            System.out.println("Date: " + entry.getDate());
            if (entry.getTitle() != null) {
                System.out.println("~" + entry.getTitle() + "~");
            }
            System.out.println(entry.getContent());
            if (entry.getMood() != null) {
                System.out.println("Mood: " + entry.getMood());
            }
            System.out.println("----------\n");
        }
    }
}