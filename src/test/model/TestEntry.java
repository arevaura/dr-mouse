package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.*;

public class TestEntry {
    private Entry testEntry;

    @BeforeEach
    void runBefore() {
        testEntry = new Entry("content");
    }

    @Test
    void testEntry() {
        assertEquals(LocalDate.now(), testEntry.getDate());
        assertNull(testEntry.getTitle());
        assertEquals("content", testEntry.getContent());
        assertNull(testEntry.getMood());
    }

    @Test
    void testSetTitle() {
        testEntry.setTitle("title");
        assertEquals("title", testEntry.getTitle());
    }
}
