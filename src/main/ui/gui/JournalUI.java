package ui.gui;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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

    private JLabel dateLabel; 
    private JLabel titleLabel;
    private JLabel contentLabel;
    private JLabel moodLabel;

    // private JTextField dateText;
    private JTextField titleText;
    private JTextArea contentText;
    private JTextField moodText;

    private JButton addEntryButton;
    private JButton viewEntriesButton;
    private JButton viewFormButton;

    private JList<String> allTitle;
    private DefaultListModel<String> data;
    private JPanel mainPanel;

    int textSize;

    public JournalUI() { // TODO: write documentation for every method
        super("Dr. Mouse"); // name of desktop window that pops up
        ImageIcon img = new ImageIcon("./graphics/icon.png");
        setIconImage(img.getImage());

        init();

        setSize(530, 500); // try getPreferredSize() // TODO: make fixed window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void init() {
        initLabels();
        initText();

        addEntryButton = new JButton("Submit Entry");
        viewEntriesButton = new JButton("View Entries");
        viewFormButton = new JButton("Write Entry");

        allTitle = new JList<>();
        data = new DefaultListModel<>();
        journal = new Journal();
        entries = journal.getEntries();
        for (Entry entry : entries) {
            data.addElement(entry.getTitle()); // has to take in a string because data rn is list of string
        }
        allTitle.setModel(data);

        mainPanel = new JPanel();

        addAllToMain();
        addEntryButton.addActionListener(this);
        viewEntriesButton.addActionListener(this);
        viewFormButton.addActionListener(this);
        add(mainPanel);
    }

    private void addAllToMain() {
        mainPanel.add(dateLabel, BorderLayout.PAGE_START);
        // mainPanel.add(dateText);
        mainPanel.add(titleLabel);
        mainPanel.add(titleText);
        mainPanel.add(contentLabel);
        mainPanel.add(contentText);
        mainPanel.add(moodLabel);
        mainPanel.add(moodText);

        mainPanel.add(addEntryButton);
        mainPanel.add(viewEntriesButton);
        mainPanel.add(allTitle);

        // mainPanel.setVisible(true);
        contentText.requestFocusInWindow();
        update();
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
        contentText = new JTextArea("REQUIRED: Click to edit.", 3, 50);
        // contentText.setFont();
        contentText.setWrapStyleWord(true); // makes sure box doesn't shrink when no text present
        moodText = new JTextField(textSize-10);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == addEntryButton) {
            title = titleText.getText();
            // date = dateText.getDate();
            content = contentText.getText();
            // mood = moodText.getMood();

            entry = new Entry(content);
            entry.setTitle(title);
            entry.setMood(mood);

            journal.addEntry(entry);
            clearFields();
        }

        if (event.getSource() == viewEntriesButton) {
            removeFields();
            mainPanel.add(viewFormButton);
            update();
        }

        if (event.getSource() == viewFormButton) {
            removeFields();
            mainPanel.remove(viewFormButton);
            addAllToMain();
            update();
        }
    }

    private void update() {
        update(getGraphics());
        this.pack();
        setSize(530, 500);
        mainPanel.setVisible(true);
    }

    private void clearFields() {
        // dateText.setText("");
        titleText.setText("");
        contentText.setText("");
        moodText.setText("");
    }

    private void removeFields() {
        mainPanel.remove(dateLabel);
        // mainPanel.remove(dateText);
        mainPanel.remove(titleLabel);
        mainPanel.remove(titleText);
        mainPanel.remove(contentLabel);
        mainPanel.remove(contentText);
        mainPanel.remove(moodLabel);
        mainPanel.remove(moodText);
        mainPanel.remove(addEntryButton);
        mainPanel.remove(viewEntriesButton);
        update();
    }
}
