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

/*
 * EFFECTS: Represents the graphical user interface for the journal application; 
 * -------- allows user to input and view journal entries;
 */
public class JournalUI extends JFrame implements ActionListener {

    // ==========--FIELDS--==========

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

    private JList<String> allTitle; // TODO: what does this do again: list of all titles of entries in journal
    private DefaultListModel<String> data;

    private JPanel mainPanel;
    private JPanel logPanel; // TODO: create new logPanelfor each saved log entry, stack in main display

    static final int TEXT_SIZE = 50;
    static final int APP_WIDTH = 600;
    static final int APP_HEIGHT = 600;

    // ==========--CONSTRUCTOR--==========

    /*
     * EFFECTS: creates a new journal user interface with a window that pops up;
     */
    public JournalUI() { // TODO: do I need to create a JFrame field, run setContentPanel() etc.?
        super("Dr. Mouse"); // name of desktop window that pops up
        setAppIcon();
        initAll();
        setLocation(400, 200); // positions window on desktop
        setSize(APP_WIDTH, APP_HEIGHT); // try getPreferredSize() // TODO: make fixed window by setting max size to this
                                        // size too
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // ==========--METHODS--==========

    /*
     * MODIFIES: this
     * EFFECTS: sets the icon of the application window to the image located in the
     * -------- graphics folder;
     */
    private void setAppIcon() {
        ImageIcon img = new ImageIcon("./graphics/icon.png");
        setIconImage(img.getImage());
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes all the components of the journal user interface;
     */
    private void initAll() {
        initLabels();
        initTexts();

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

    /*
     * MODIFIES: this
     * EFFECTS: adds all the components to the main panel;
     */
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

    /*
     * MODIFIES: this
     * EFFECTS: initializes all the labels of the journal user interface;
     */
    private void initLabels() {
        dateLabel = new JLabel("Fill in the boxes below and submit to create a new entry (content necessary)."
                + LocalDate.now().toString());
        titleLabel = new JLabel("Title");
        contentLabel = new JLabel("Content");
        moodLabel = new JLabel("Mood");
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes all the text fields of the journal user interface;
     */
    private void initTexts() {
        // dateText = new JTextField(textSize-10);
        titleText = new JTextField(TEXT_SIZE - 5);
        contentText = new JTextArea("REQUIRED: Click to edit.", 3, 50);
        // contentText.setFont();
        contentText.setWrapStyleWord(true); // makes sure box doesn't shrink when no text present
        moodText = new JTextField(TEXT_SIZE - 10);
    }

    /*
     * MODIFIES: this
     * EFFECTS: updates the journal user interface based on the event that occurred;
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        checkAddEntryButton(event);
        checkViewEntriesButton(event);
        checkViewFormButton(event);
    }

    /*
     * MODIFIES: this
     * EFFECTS: checks if the viewFormButton was clicked and displays the entry
     * -------- input form of the journal if it was;
     */
    private void checkViewFormButton(ActionEvent event) {
        if (event.getSource() == viewFormButton) {
            removeComponents();
            mainPanel.remove(viewFormButton);
            addAllToMain();
            update();
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: checks if the viewEntriesButton was clicked and displays the
     * -------- journal's entire entry log if it was;
     */
    private void checkViewEntriesButton(ActionEvent event) {
        if (event.getSource() == viewEntriesButton) {
            removeComponents();
            mainPanel.add(viewFormButton);
            update();
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: checks if the add entry button was clicked and adds the entry to the
     * -------- journal if it was;
     */
    private void checkAddEntryButton(ActionEvent event) {
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
    }

    /*
     * MODIFIES: this
     * EFFECTS: updates the journal user interface graphics;
     */
    private void update() {
        update(getGraphics());
        this.pack();
        setSize(APP_WIDTH, APP_HEIGHT);
        mainPanel.setVisible(true);
    }

    /*
     * MODIFIES: this
     * EFFECTS: clears all the text fields of the journal user interface;
     */
    private void clearFields() {
        // dateText.setText("");
        titleText.setText("");
        contentText.setText("");
        moodText.setText("");
    }

    /*
     * MODIFIES: this
     * EFFECTS: removes all the components of the journal user interface;
     */
    private void removeComponents() {
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
