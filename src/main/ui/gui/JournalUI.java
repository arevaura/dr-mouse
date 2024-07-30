package ui.gui;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
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

public class JournalUI extends JFrame implements ActionListener {

    private Journal journal;
    private ArrayList<Entry> entries;
    private Entry entry;
    private String title;
    private String content;
    private String mood;

    JLabel dateLabel; 
    JLabel titleLabel; // TODO: make all fields private
    JLabel contentLabel;
    JLabel moodLabel;

    // JTextField dateText;
    JTextField titleText;
    JTextField contentText;
    JTextField moodText;

    JButton addEntryButton;
    JButton viewButton;
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
        viewButton = new JButton("View Entries");

        allTitle = new JList<>();
        data = new DefaultListModel<>();
        journal = new Journal();
        entries = journal.getEntries();
        for (Entry entry : entries) {
            data.addElement(entry.getTitle()); // has to take in a string because data rn is list of string
        }
        allTitle.setModel(data);

        journalPanel = new JPanel();

        addAllToPanel();
        addEntryButton.addActionListener(this);
        viewButton.addActionListener(this);
        add(journalPanel);
    }

    private void addAllToPanel() {
        journalPanel.add(dateLabel);
        // journalPanel.add(dateText);
        journalPanel.add(titleLabel);
        journalPanel.add(titleText);
        journalPanel.add(contentLabel);
        journalPanel.add(contentText);
        journalPanel.add(moodLabel);
        journalPanel.add(moodText);

        journalPanel.add(addEntryButton);
        journalPanel.add(viewButton);
        journalPanel.add(allTitle);
    }

    private void initLabels() {
        dateLabel = new JLabel("Fill in the boxes below and submit to create a new entry (content necessary)." + LocalDate.now().toString());
        titleLabel = new JLabel("Title");
        contentLabel = new JLabel("Content");
        moodLabel = new JLabel("Mood");
    }

    private void initText() {
        textSize = 50;
        // dateText = new JTextField(textSize-10);
        titleText = new JTextField(textSize-5);
        contentText = new JTextField(textSize);
        moodText = new JTextField(textSize-10);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == addEntryButton) {
            title = titleText.getText();
            // date = dateText.getDate();
            // content = contentText.getContent();
            // mood = moodText.getMood();

            entry = new Entry(content);
            entry.setTitle(title);
            entry.setMood(mood);

            journal.addEntry(entry);
            clearFields();
        }
    }

    private void clearFields() {
        // dateText.setText("");
        titleText.setText("");
        contentText.setText("");
        moodText.setText("");
    }
}
