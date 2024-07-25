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
class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Journal jr = new Journal();
            jr.getClass(); // to suppress error for not using jr anywhere in code
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyJournal() {
        try {
            Journal jr = new Journal();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyJournal.json");
            writer.open();
            writer.write(jr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyJournal.json");
            jr = reader.read();
            // assertEquals("My workroom", wr.getName());
            assertEquals(0, jr.getNumEntries());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralJournal() {
        try {
            Journal jr = new Journal();
            jr.addEntry(new Entry("hello"));
            jr.addEntry(new Entry("content"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralJournal.json");
            writer.open();
            writer.write(jr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralJournal.json");
            jr = reader.read();
            // assertEquals("My work room", wr.getName());
            List<Entry> entries = jr.getEntries();
            assertEquals(2, entries.size());
            checkContent("hello", entries.get(0));
            checkContent("content", entries.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}