package persistence;

// import model.Category;
import model.Entry;
import model.Journal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

/*
 * Special Note: The code for all the following methods and this class was based on
 * ------------- sample code provided from the UBC CPSC210 2024 summer course
 * ------------- and then later adapted to function in this program.
 */
// Represents a reader that reads journal from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads journal from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Journal read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseJournal(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses journal from JSON object and returns it
    private Journal parseJournal(JSONObject jsonObject) {
        Journal jr = new Journal();
        addEntries(jr, jsonObject);
        return jr;
    }

    // MODIFIES: jr
    // EFFECTS: parses entries from JSON object and adds them to journal
    private void addEntries(Journal jr, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("entries");
        for (Object json : jsonArray) {
            JSONObject nextEntry = (JSONObject) json;
            addEntry(jr, nextEntry);
        }
    }

    // MODIFIES: jr
    // EFFECTS: parses entry from JSON object and adds it to journal
    private void addEntry(Journal jr, JSONObject jsonObject) {
        String date = jsonObject.getString("date");
        String content = jsonObject.getString("content");
        String title = jsonObject.getString("title");
        String mood = jsonObject.getString("mood");
        Entry entry = new Entry("setters from JsonReader.java not working if you see this");
        entry.setDate(date);
        entry.setTitle(title);
        entry.setContent(content);
        entry.setMood(mood);
        jr.addEntry(entry);
    }
}
