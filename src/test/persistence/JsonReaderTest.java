package persistence;

// import model.Category;
import model.Entry;
import model.Journal;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Special Note: The code for all the following methods and this class was based on
 * ------------- sample code provided from the UBC CPSC210 2024 summer course
 * ------------- and then later adapted to function in this program.
 */
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Journal jr = reader.read();
            jr.getClass(); // to suppress error for not using jr anywhere in code
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyJournal() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyJournal.json");
        try {
            Journal jr = reader.read();
            // assertEquals("My work room", wr.getName());
            assertEquals(0, jr.getNumEntries());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralJournal() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralJournal.json");
        try {
            Journal jr = reader.read();
            List<Entry> entries = jr.getEntries();
            assertEquals(2, entries.size());
            // test first entry (has all metadata)
            checkDate("2024-07-24", entries.get(0));
            checkTitle("GREETING", entries.get(0));
            checkContent("hello", entries.get(0));
            checkMood("friendly", entries.get(0));
            // test second entry (has some metadata)
            checkTitle("FAREWELL", entries.get(1));
            checkContent("goodbye", entries.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}