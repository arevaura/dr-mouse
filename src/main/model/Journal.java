package model;

import java.util.ArrayList;

/**
 * Represents a journal with a list of entries and a number of entries.
 *  - A journal can add, remove, and filter entries by date, mood, and keyword.
 *  - A journal can also return all entries logged in the journal.
 *  - A journal can also return the number of entries logged in the journal.
 */
public class Journal {
    // ==========--FIELDS--==========
    private ArrayList<Entry> entries;
    private int numEntries;

    // ==========--CONSTRUCTOR--==========
    /*
     * EFFECTS: creates a new journal object with an empty list of entries and sets
     *          the number of entries to 0;
     */
    public Journal() {
        entries = new ArrayList<>();
        numEntries = 0;
    }

    // ==========--GETTER-METHODS--==========
    /*
     * EFFECTS: returns a list of all the entries logged in the journal;
     */
    public ArrayList<Entry> getEntries() {
        return entries;
    }

    /*
     * EFFECTS: returns the number of entries logged in the journal;
     */
    public int getNumEntries() {
        return numEntries;
    }

    // ==========--FUNCTIONAL-METHODS--==========
    /*
     * MODIFIES: this
     * EFFECTS: adds a new entry to the journal;
     *          increments the number of entries by 1;
     */
    public void addEntry(Entry entry) {
        entries.add(entry);
        numEntries++;
    }

    /*
     * REQUIRES: journal must contain the given entry
     * MODIFIES: this
     * EFFECTS: removes an entry from the journal;
     *          increments the number of entries by -1;
     */
    public void removeEntry(Entry entry) {
        entries.remove(entry);
        numEntries--;
    }

    /*
     * REQUIRES: date must follow "yyyy-mm-dd" string format
     * EFFECTS: returns all entries in the journal that match the given date;
     *          if no entries have the given date, returns an empty list;
     */
    public ArrayList<Entry> filterEntriesByDate(String date) {
        ArrayList<Entry> entriesByDate = new ArrayList<>();
        for (Entry entry : entries) {
            if (entry.getDate().toString().equals(date)) {
                entriesByDate.add(entry);
            }
        }
        return entriesByDate;
    }

    /*
     * REQUIRES: mood is not null or ""
     * EFFECTS: returns all entries in the journal that match the given mood;
     *          if no entries have the given mood, returns an empty list;
     */
    public ArrayList<Entry> filterEntriesByMood(String mood) {
        ArrayList<Entry> entriesByMood = new ArrayList<>();
        for (Entry entry : entries) {
            if (entry.getMood().toUpperCase().equals(mood.toUpperCase())) {
                entriesByMood.add(entry);
            }
        }
        return entriesByMood;
    }

    /*
     * REQUIRES: keyword is not null or ""
     * EFFECTS: returns all entries in the journal that contain the given keyword;
     *          if no entries contain the given keyword, returns an empty list;
     */
    public ArrayList<Entry> filterEntriesByKeyword(String keyword) {
        ArrayList<Entry> entriesByKeyword = new ArrayList<>();
        for (Entry entry : entries) {
            if (entry.getContent().contains(keyword)) {
                entriesByKeyword.add(entry);
            }
        }
        return entriesByKeyword;
    }
}
