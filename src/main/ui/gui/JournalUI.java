package ui.gui;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Entry;
import model.Journal;

public class JournalUI extends JFrame {

    private Journal journal;
    private ArrayList<Entry> entries;

    JLabel dateLabel; // TODO: make all fields private
    JLabel titleLabel;
    JLabel contentLabel;
    JLabel moodLabel;

    JLabel viewLabel;

    JTextField dateText;
    JTextField titleText;
    JTextField contentText;
    JTextField moodText;

    JButton addEntryButton;
    JList<String> allTitle;
    DefaultListModel<String> data;
    JPanel journalPanel;

    int textSize;

    public JournalUI() { // TODO: write documentation for every method
        super("Dr. Mouse"); // name of desktop window that pops up

        init();

        setSize(530, 500); // try getPreferredSize()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void init() {
        initLabels();
        initText();

        addEntryButton = new JButton("Add");

        allTitle = new JList<>();
        data = new DefaultListModel<>();
        journal = new Journal();
        entries = journal.getEntries();

        for (Entry entry : entries) {
            data.addElement(entry.getTitle()); // has to take in a string because data rn is list of string
        }

        journalPanel = new JPanel();

        addAllToPanel();
        add(journalPanel);
    }

    private void addAllToPanel() {
        journalPanel.add(dateLabel);
        journalPanel.add(dateText);
        journalPanel.add(titleLabel);
        journalPanel.add(titleText);
        journalPanel.add(contentLabel);
        journalPanel.add(contentText);
        journalPanel.add(moodLabel);
        journalPanel.add(moodText);

        journalPanel.add(addEntryButton);

        journalPanel.add(viewLabel);
    }

    private void initLabels() {
        dateLabel = new JLabel("Date");
        titleLabel = new JLabel("Title");
        contentLabel = new JLabel("Content");
        moodLabel = new JLabel("Mood");

        viewLabel = new JLabel("View All Journals");
    }

    private void initText() {
        textSize = 50;
        dateText = new JTextField(textSize);
        titleText = new JTextField(textSize);
        contentText = new JTextField(textSize);
        moodText = new JTextField(textSize);
    }
}
