package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.*;
import java.util.ArrayList;

public class TestJournal {
    private Journal diaryJournal;
    private Entry testEntry1;
    private Entry testEntry2;
    private Entry testEntry3;

    @BeforeEach
    void runBefore() {
        diaryJournal = new Journal();
        testEntry1 = new Entry("test 1");
        testEntry2 = new Entry("test 11");
        testEntry3 = new Entry("test 111");
    }

    @Test
    void testJournal() {
        assertTrue(diaryJournal.getEntries().isEmpty());
        assertEquals(0, diaryJournal.getNumEntries());
    }

    @Test
    void testAddEntry() {
        diaryJournal.addEntry(testEntry1);
        assertEquals(1, diaryJournal.getNumEntries());
        assertTrue(diaryJournal.getEntries().contains(testEntry1));
    }

    @Test
    void testMultipleAddEntry() {
        diaryJournal.addEntry(testEntry1);
        diaryJournal.addEntry(testEntry2);
        diaryJournal.addEntry(testEntry3);
        assertEquals(3, diaryJournal.getNumEntries());
        assertTrue(diaryJournal.getEntries().contains(testEntry1));
        assertTrue(diaryJournal.getEntries().contains(testEntry2));
        assertTrue(diaryJournal.getEntries().contains(testEntry3));
    }

    @Test
    void testRemoveEntry() {
        diaryJournal.addEntry(testEntry1);
        diaryJournal.removeEntry(testEntry1);
        assertEquals(0, diaryJournal.getNumEntries());
    }

    @Test
    void testMultipleRemoveEntry() {
        diaryJournal.addEntry(testEntry1);
        diaryJournal.addEntry(testEntry2);
        diaryJournal.addEntry(testEntry3);
        diaryJournal.removeEntry(testEntry1);
        diaryJournal.removeEntry(testEntry3);
        assertEquals(1, diaryJournal.getNumEntries());
        assertFalse(diaryJournal.getEntries().contains(testEntry1));
        assertTrue(diaryJournal.getEntries().contains(testEntry2));
    }

    @Test
    void testReset() {
        diaryJournal.addEntry(testEntry1);
        diaryJournal.addEntry(testEntry2);
        diaryJournal.addEntry(testEntry3);
        assertEquals(3, diaryJournal.getNumEntries());
        diaryJournal.reset();
        assertEquals(0, diaryJournal.getNumEntries());
        assertTrue(diaryJournal.getEntries().isEmpty());

        diaryJournal.reset();
        assertEquals(0, diaryJournal.getNumEntries());
        assertTrue(diaryJournal.getEntries().isEmpty());
    }

    @Test
    void testFilterEntriesByDate() {
        diaryJournal.addEntry(testEntry1); // "test 1"
        diaryJournal.addEntry(testEntry2); // "test 11"
        ArrayList<Entry> filteredList = diaryJournal.filterEntriesByDate(LocalDate.now().toString());
        assertEquals(2, filteredList.size());
        assertTrue(filteredList.contains(testEntry1));
        assertFalse(filteredList.contains(testEntry3)); // "test 111"
        assertEquals(0, diaryJournal.filterEntriesByDate("2022-02-22").size());
    }

    @Test
    void testFilterEntriesByMood() {
        testEntry1.setMood("happy");
        testEntry2.setMood("happy");
        testEntry3.setMood("sad");
        diaryJournal.addEntry(testEntry1);
        diaryJournal.addEntry(testEntry2);
        diaryJournal.addEntry(testEntry3);
        ArrayList<Entry> filteredList = diaryJournal.filterEntriesByMood("happy");
        assertEquals(2, filteredList.size());
        assertTrue(filteredList.contains(testEntry1));
        assertTrue(filteredList.contains(testEntry2));
        assertFalse(filteredList.contains(testEntry3));
        assertEquals(0, diaryJournal.filterEntriesByMood("angry").size());
    }

    @Test
    void testFilterEntriesByKeyword() {
        testEntry1.setContent("I am happy");
        testEntry2.setContent("I am sad");
        testEntry2.setContent("I am happy");
        testEntry3.setContent("I am sad");
        diaryJournal.addEntry(testEntry1);
        diaryJournal.addEntry(testEntry2);
        diaryJournal.addEntry(testEntry3);
        ArrayList<Entry> filteredList = diaryJournal.filterEntriesByKeyword("happy");
        assertEquals(2, filteredList.size());
        assertTrue(filteredList.contains(testEntry1));
        assertTrue(filteredList.contains(testEntry2));
        assertFalse(filteredList.contains(testEntry3));
        assertEquals(0, diaryJournal.filterEntriesByKeyword("angry").size());
    }
}