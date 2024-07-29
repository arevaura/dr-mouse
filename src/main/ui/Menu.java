package ui;

import java.util.Scanner;

import model.*;

/*
 * This class stores all the commands that correspond to each possible action you can perform in the program.
 * For example, users are able to add and remove entries in this program and so the corresponding 
 * add and remove functions are implemented in this Menu class.
 * Any immediate helper method for the main action methods are implemented directly below the appropriate method.
 */
public class Menu {
    /*
     * EFFECTS: executes the exit action by printing a goodbye message;
     */
    protected static Boolean exit() {
        Terminal.clearScreen();
        System.out.println("Dr. Mouse says goodbye and have a great day!\nSqueak! =.=\n");
        System.exit(0);
        return true;
    }

    /*
     * EFFECTS: executes the view action by printing all entries in the journal;
     */
    protected static Boolean view(Journal journal) {
        Terminal.clearScreen();
        System.out.println("Here are all your log entries:\n====================\n");
        Helper.printEntries(journal.getEntries());
        return true;
    }

    /*
     * MODIFIES: entries ArrayList in journal
     * EFFECTS: executes the add action by prompting the user to create a new entry;
     */
    protected static Boolean add(Scanner input, Journal journal) {
        Terminal.clearScreen();
        System.out.println("Please type your new log entry:");
        String text = input.nextLine();
        Entry entry = new Entry(text);
        journal.addEntry(entry);
        System.out.println("\nAdding your new log entry...");
        System.out.println("Your new log entry has been added!");
        System.out.println("Edit the entry to add an entry title and/or mood of the day.");
        return true;
    }

    /*
     * MODIFIES: entries ArrayList in journal
     * EFFECTS: executes the remove action by prompting the user to select an entry
     * -------- to remove;
     */
    protected static Boolean remove(Scanner input, Journal journal) {
        Terminal.clearScreen();
        Entry entry = Helper.selectEntry(input, journal);
        journal.removeEntry(entry);
        System.out.println("\nRemoving your selected log entry...");
        System.out.println("Your selected log entry has been removed!");
        return true;
    }

    /*
     * MODIFIES: entry object from entries ArrayList in journal
     * EFFECTS: executes the edit action by prompting the user to select an entry to
     * -------- modify;
     */
    protected static Boolean edit(Scanner input, Journal journal) {
        Terminal.clearScreen();
        Entry entry = Helper.selectEntry(input, journal);
        System.out.println("\nPlease type 1 to edit the title, 2 to edit the content, or 3 to edit the mood:");
        // TODO: tell users that each entry has three fields (otherwise they will be
        // confused here because they didn't enter a mood)
        // ArrayList<Integer> options = new ArrayList<>();
        // Collections.addAll(options, 1, 2, 3);
        // switch (Helper.acceptInt(input)) {
        //     case 1:
        //         editTitle(input, entry);
        //         break;
        //     case 2:
        //         editContent(input, entry, journal);
        //         break;
        //     case 3:
        //         editMood(input, entry);
        //         break;
        //     default:
        //         System.out.println("Squeak! That wasn't one of the options. Let's try again.");
        //         edit(input, journal);
        //         break;
        // }
        int action = input.nextInt();
        input.nextLine(); // Consume newline left-over
        if (action == 1) {
            editTitle(input, entry);
        } else if (action == 2) {
            editContent(input, entry, journal);
        } else if (action == 3) {
            editMood(input, entry);
        } else {
            System.out.println("\nSqueak! That wasn't one of the edit options!");
            edit(input, journal);
        }
        System.out.println("\nEditing your selected log entry...");
        System.out.println("Your selected log entry has been edited!");
        return true;
    }

    /*
     * MODIFIES: entry;
     * EFFECTS: updates the title field of the given entry to the given input;
     */
    private static void editTitle(Scanner input, Entry entry) {
        System.out.println("Please enter the new title for your log entry:");
        String title = input.nextLine().toUpperCase();
        entry.setTitle(title);
    }

    /*
     * MODIFIES: entry;
     * EFFECTS: updates the content field of the given entry to the given input;
     */
    private static void editContent(Scanner input, Entry entry, Journal journal) {
        System.out.println("Would you like to restart the entry (1) or continue it (2)?");
        switch (Helper.acceptInt(input)) {
            case 1:
                System.out.println("Please enter the new text for your log entry:");
                entry.setContent(input.nextLine());
                break;
            case 2:
                String original = entry.getContent();
                System.out.println("Here is your original entry:\n" + original);
                System.out.println("\nPlease continue the text for your log entry (starts on a new line):");
                entry.setContent(original + "\n[EDIT] " + input.nextLine());
                break;
            default:
                System.out.println("Error: Something went wrong while editing entry.");
                break;
        }
    }

    /*
     * MODIFIES: entry;
     * EFFECTS: updates the mood field of the given entry to the given input;
     */
    private static void editMood(Scanner input, Entry entry) {
        System.out.println("Please enter your mood for this log entry:");
        entry.setMood(input.nextLine());
    }

    /*
     * // TODO: filter method not yet incorporated
     * EFFECTS: executes the filter action by prompting the user to enter a date
     * to filter entries;
     */
    protected static Boolean filter(Scanner input, Journal journal) {
        Terminal.clearScreen();
        System.out.println("Please enter 1 to filter by date, 2 to filter by mood, or 3 to filter by keyword:");
        int action = input.nextInt();
        // input.nextLine(); // Consume newline left-over
        String search = null;
        if (action == 1) {
            System.out.println("Please enter the date you want to filter by (i.e. \"2021-01-01\"):");
            search = input.nextLine().toLowerCase();
            Helper.printEntries(journal.filterEntriesByDate(search));
        } else if (action == 2) {
            System.out.println("Please enter the mood you want to filter by (i.e. \"happy\"):");
            search = input.nextLine().toLowerCase();
            Helper.printEntries(journal.filterEntriesByMood(search));
        } else if (action == 3) {
            System.out.println("Please enter the keyword you want to filter by (i.e. \"cheese\"):");
            search = input.nextLine().toLowerCase();
            Helper.printEntries(journal.filterEntriesByKeyword(search));
        } else {
            System.out.println("\nSqueaky cheese! That wasn't one of the filter options!");
            filter(input, journal);
        }
        return true;
    }

    /*
     * EFFECTS: prints the number of entries in the journal;
     */
    protected static Boolean total(Journal journal) {
        Terminal.clearScreen();
        if (journal.getNumEntries() == 0) {
            System.out.println("You don't have any saved log entries.\nWrite one now!");
        } else if (journal.getNumEntries() == 1) {
            System.out.println("You have 1 saved log entry. Keep going!");
        } else {
            System.out.println("\nYou have " + journal.getNumEntries() + " saved log entries. Awesome!");
        }
        return true;
    }

    /*
     * MODIFIES: journal fields
     * EFFECTS: sets the journal fields to their initial state upon creation
     */
    protected static Boolean reset(Scanner input, Journal journal) {
        Terminal.clearScreen();
        System.out.println("This step is irreversible. Are you sure you want to clear your journal?");
        System.out.println("Type 'yes' to proceed. Otherwise this action will be cancelled.");
        String answer = input.nextLine().toLowerCase();
        if (answer.equals("yes")) {
            System.out.println("\nAlright, I'll get you a new journal.");
            System.out.println("Let's start fresh again...\nlike the smell of my favourite blue cheese! YUM :D");
            journal.reset();
        } else {
            System.out.println("\nOkay, this action will be cancelled...");
        }
        return true;
    }
}
