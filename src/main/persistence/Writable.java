package persistence;

import org.json.JSONObject;

/*
 * Special Note: The code for the following interface was based on
 * ------------- sample code provided from the UBC CPSC210 2024 summer course
 * ------------- and then later adapted to function in this program.
 */
public interface Writable {
    /*
     * EFFECTS: returns this as JSON object;
     */
    JSONObject toJson();
}
