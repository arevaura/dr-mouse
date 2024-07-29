package model;

import java.time.*;

import org.json.JSONObject;

import persistence.Writable;

/**
 * Represents an entry in a journal with a date, content of the entry,
 * and optional title and mood fields.
 * - All fields can be retrieved.
 * - The date field is automatically set to the date the entry is created.
 * - The title, content, and mood fields can be set by the user.
 * - The content field is required as input upon creating an Entry,
 * --but it can be rewritten using its setter method.
 * 
 * Special Note: The code for the following method in this class was based on
 * ------------- sample code provided from the UBC CPSC210 2024 summer course
 * ------------- and then later adapted to function in this program:
 * ------------- 1) [public JSONObject] toJson()
 */
public class Entry implements Writable {
    // ==========--FIELDS--==========
    private LocalDate date;
    private String title;
    private String content;
    private String mood;

    // ==========--CONSTRUCTOR--==========
    /*
     * EFFECTS: creates a new Entry object
     * with date, title, content, and mood fields;
     * the date the entry is created is automatically saved to the date field;
     * String fields are set to null so user can manually choose to set them;
     */
    public Entry(String text) {
        this.date = LocalDate.now();

        this.title = "";
        this.content = text;
        this.mood = "";
    }

    // ==========--GETTER-METHODS--==========
    /*
     * EFFECTS: returns the date of the entry;
     */
    public String getDate() {
        return date.toString();
    }

    /*
     * EFFECTS: returns the title of the entry;
     */
    public String getTitle() {
        return title;
    }

    /*
     * EFFECTS: returns the content of the entry;
     */
    public String getContent() {
        return content;
    }

    /*
     * EFFECTS: returns the user's mood for the entry;
     */
    public String getMood() {
        return mood;
    }

    // ==========--SETTER-METHODS--==========
    /*
     * MODIFIES: this
     * EFFECTS: sets the date of the entry to the given date;
     */
    public void setDate(String date) {
        this.date = LocalDate.parse(date);
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets the title of the entry to the given title;
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets the content of the entry with the given content text;
     */
    public void setContent(String content) {
        this.content = content;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets the user's mood for the entry to the given mood;
     */
    public void setMood(String mood) {
        this.mood = mood;
    }

    /*
     * EFFECTS: saves all entry metadata as JSONObject
     */
    @Override // from Writable interface
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date", date);
        json.put("title", title);
        json.put("content", content);
        json.put("mood", mood);
        return json;
    }
}
