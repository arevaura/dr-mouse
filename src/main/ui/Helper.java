package ui;

import java.util.List;
import java.util.Scanner;

import model.*;

/*
 * This class was created to store helper methods for the main ui classes.
 * See EFFECTS clause of each method to understand what they do.
 */
public class Helper {
    /*
     * EFFECTS: prints all saved entries in a journal;
     */
    protected static void printEntries(List<Entry> entries) {
        for (Entry entry : entries) {
            System.out.println("Date: " + entry.getDate());
            if (entry.getTitle() != null) {
                System.out.println("~ " + entry.getTitle() + " ~");
            }
            System.out.println(entry.getContent());
            if (entry.getMood() != null) {
                System.out.println("Mood: " + entry.getMood());
            }
            System.out.println("----------\n");
        }
    }

    /*
     * EFFECTS: prompts user to select an entry and returns the selected entry;
     */
    protected static Entry selectEntry(Scanner input, Journal journal) {
        System.out.println("\nPlease enter the number of the entry you want to modify (i.e. \"1\"):");
        // TODO: remove/edit fn breaks when user enters non-option (not a valid number)
        for (int i = 0; i < journal.getNumEntries(); i++) {
            System.out.println(i + 1 + ". \"" + journal.getEntries().get(i).getContent() + "\"");
        }
        int selection = input.nextInt();
        Entry entry = journal.getEntries().get(selection - 1);
        System.out.println("\nSelecting your log entry now...");
        System.out.println("Success! You have selected the following entry:");
        System.out.println("\"" + entry.getContent() + "\"");
        return entry;
    }
}
