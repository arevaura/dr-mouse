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
        assertEquals(LocalDate.now().toString(), testEntry.getDate());
        assertEquals("", testEntry.getTitle());
        assertEquals("content", testEntry.getContent());
        assertEquals("", testEntry.getMood());
    }

    @Test
    void testSetDate() {
        testEntry.setDate("2024-08-20");
        // LocalDate birthday = LocalDate.of(2024, 8, 20);
        assertEquals("2024-08-20", testEntry.getDate());
    }

    @Test
    void testSetTitle() {
        testEntry.setTitle("title");
        assertEquals("title", testEntry.getTitle());
    }
}
