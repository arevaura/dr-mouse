package persistence;

// import model.Category;
import model.Entry;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * Special Note: The code for all the following methods and this class was based on
 * ------------- sample code provided from the UBC CPSC210 2024 summer course
 * ------------- and then later adapted to function in this program.
 */
public class JsonTest {
    protected void checkDate(String date, Entry entry) {
        assertEquals(date, entry.getDate());
    }

    protected void checkTitle(String title, Entry entry) {
        assertEquals(title, entry.getTitle());
    }

    protected void checkContent(String content, Entry entry) {
        assertEquals(content, entry.getContent());
    }

    protected void checkMood(String mood, Entry entry) {
        assertEquals(mood, entry.getMood());
    }
}
