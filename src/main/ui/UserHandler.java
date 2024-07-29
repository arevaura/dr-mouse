package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import model.*;
import persistence.*;

/**
 * Represents a handler that manages the user's interaction with the journal.
 * Users can view, add, remove, and edit entries in the journal.
 * All feedback messages and UI are displayed in the terminal.
 */
public class UserHandler {
    private static final String JSON_STORE = "./data/journal.json";
    private Journal journal = new Journal();
    private Scanner input = new Scanner(System.in);
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);

    // ==========--CONSTRUCTOR--==========
    /**
     * Constructs a new UserHandler object and prompts the user to choose an action.
     * A menu of options is given for the user's reference.
     */
    public UserHandler() throws IOException, FileNotFoundException {
        input.nextLine(); // consumes .read() from Main.java
        System.out.println("Would you like to load saved data?"); 
        System.out.println("Enter \"yes\" to load.\nOtherwise, enter anything to proceed with an empty journal.");
        String response = input.nextLine().toLowerCase();
        if (response.equals("yes")) {
            loadJournal();
        }
        while (true) {
            if (chooseAction()) {
                System.out.println("\nEnter [any key] to return to the main menu.");
                Terminal.waitForKey();
            }
            System.out.println("Returning to the main menu...");
            System.out.println("\n====================\n");
        }
    }

    // ==========--HELPER-METHODS--==========

    /*
     * EFFECTS: prints the main menu options;
     */
    private void printMenuOptions() {
        System.out.println("Please choose one of the following options (i.e. \"add\"):\n");
        System.out.println("exit --> close this journal");
        System.out.println("add --> log a new entry");
        if (journal.getNumEntries() != 0) {
            System.out.println("remove --> delete a log entry");
            System.out.println("view --> view all your saved log entries");
            System.out.println("edit --> modify a saved log entry");
            System.out.println("reset --> clear all entries from journal permanently");
            // System.out.println("filter - view log entries using filter");
        }
        System.out.println("total --> view the number of logged entries");
        System.out.println("save --> save journal data");
        System.out.println("load --> load saved journal data");
        // TODO: note: will override current journal - print this to notify users
    }

    /*
     * EFFECTS: saves the journal to file;
     */
    private Boolean saveJournal() {
        try {
            jsonWriter.open();
            jsonWriter.write(journal);
            jsonWriter.close();
            System.out.println("Saved journal to " + JSON_STORE + "! \nSQUEAK");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
        return true;
    }

    /*
     * MODIFIES: this;
     * EFFECTS: loads journal from file;
     */
    private Boolean loadJournal() {
        try {
            journal = jsonReader.read();
            System.out.println("Loaded journal from " + JSON_STORE + "! \nSQUEAK");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        return true;
    }

    /*
     * EFFECTS: sets user up to choose menu action from available choices;
     */
    private Boolean chooseAction() {
        //Terminal.clearScreen(); // TODO: remove?
        printMenuOptions();
        return userSelectsFromMenu();
    }

    /*
     * EFFECTS: prompts user to choose action and follows throught with command;
     */
    private Boolean userSelectsFromMenu() {
        switch (input.nextLine().toLowerCase()) { // TODO: switch input reqs to contains words vs exact
            case "exit":
                return Menu.exit();
            case "save":
                return saveJournal();
            case "load":
                return loadJournal();
            case "view":
                return Menu.view(journal);
            case "add":
                return Menu.add(input, journal);
            case "remove":
                return Menu.remove(input, journal);
            case "edit":
                return Menu.edit(input, journal);
            case "total":
                return Menu.total(journal);
            case "reset":
                return Menu.reset(input, journal);
            default:
                System.out.println("\nWhiskers! That wasn't one of the menu options!");
                return false;
        }
    }
}