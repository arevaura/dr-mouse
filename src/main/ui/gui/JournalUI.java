package ui.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionListener;

import model.Entry;
import model.Journal;
import persistence.JsonReader;
import persistence.JsonWriter;

/*
 * EFFECTS: Represents the graphical user interface for the journal application; 
 * -------- allows user to input and view journal entries;
 */
public class JournalUI extends JFrame implements ActionListener {

    // ==========--FIELDS--==========

    private static final String JSON_STORE = "./data/journal.json";
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);

    private Journal journal;
    private Entry entry;

    private String title;
    private String content;
    private String mood;

    private JLabel dateLabel;
    private JLabel titleLabel;
    private JLabel contentLabel;
    private JLabel moodLabel;

    private JTextField titleText;
    private JTextArea contentText;
    private JTextField moodText;

    private JButton loadButton;
    private JButton saveButton;
    private JButton viewFormButton;
    private JButton addEntryButton;
    private JButton removeEntryButton;
    private Boolean isRemoveEntryButtonClicked;
    private JButton viewEntriesButton;

    private JPanel homePage; // page with just buttons, redirect page BOXLAYOUT
    private JPanel formPage; // page for adding new entry BORDERLAYOUT
    private JPanel entryPanel; // section for entering entry info SPRINGLAYOUT
    private JPanel buttonPanel; // section for showing button options FLOWLAYOUT
    private JPanel logPage; // page to see log entries BORDERLAYOUT
    private JList<Entry> entriesList; // section to view entries and let user select one JLIST

    static final int TEXT_SIZE = 50;
    static final int APP_WIDTH = 600;
    static final int APP_HEIGHT = 600;

    // ==========--CONSTRUCTOR--==========

    /*
     * EFFECTS: creates a new journal user interface with a window that pops up;
     */
    public JournalUI() {
        super("Dr. Mouse"); // name of desktop window that pops up
        setAppIcon();
        initAll();
        setLocation(400, 200); // positions window on desktop at given x and y coordinates
        // setResizable(false); // prevents user from resizing window
        setSize(APP_WIDTH, APP_HEIGHT); // sets size of window
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
        journal = new Journal();

        initButtons();
        buttonPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.CENTER);
        buttonPanel.setLayout(flowLayout);
        
        setUpHomePage();
        setUpFormPage();
        setUpLogPage();
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets up the home page of the journal user interface;
     */
    private void setUpHomePage() {
        homePage = new JPanel();
        homePage.setLayout(new BoxLayout(homePage, BoxLayout.Y_AXIS));

        ImageIcon image = new ImageIcon("./graphics/confusedmouse.jpg");
        homePage.add(new JLabel(image));

        homePage.add(new JLabel("Welcome to Dr. Mouse!"));
        homePage.add(Box.createRigidArea(new Dimension(0, 30)));
        homePage.add(viewFormButton);
        homePage.add(Box.createRigidArea(new Dimension(0, 15)));
        homePage.add(viewEntriesButton);
        homePage.add(Box.createRigidArea(new Dimension(0, 30)));
        homePage.add(loadButton);

        add(homePage);
        homePage.setVisible(true);
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets up the form page of the journal user interface;
     */
    private void setUpFormPage() {
        formPage = new JPanel();
        formPage.setLayout(new BorderLayout());

        initFormLabels();
        initFormTexts();
        addComponentsToFormPage();

        add(formPage);
        formPage.setVisible(false);
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets up the log page of the journal user interface;
     */
    private void setUpLogPage() {
        logPage = new JPanel();
        logPage.setLayout(new BorderLayout());

        add(logPage);
        logPage.setVisible(false);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes all the buttons of the journal user interface;
     */
    private void initButtons() {
        loadButton = new JButton("Load Saved Entries");
        loadButton.addActionListener(this);

        saveButton = new JButton("Save Entries");
        saveButton.addActionListener(this);

        viewFormButton = new JButton("Write Entry");
        viewFormButton.addActionListener(this);

        addEntryButton = new JButton("Submit Entry");
        addEntryButton.addActionListener(this);

        removeEntryButton = new JButton("Delete Selected Entry");
        removeEntryButton.addActionListener(this);
        isRemoveEntryButtonClicked = false;

        viewEntriesButton = new JButton("View Entries");
        viewEntriesButton.addActionListener(this);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes all the labels of the journal user interface;
     */
    private void initFormLabels() {
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
    private void initFormTexts() {
        titleText = new JTextField(TEXT_SIZE - 5);
        contentText = new JTextArea("REQUIRED: Click to edit.", 3, 58);
        contentText.setWrapStyleWord(true); // makes sure box doesn't shrink when no text present
        moodText = new JTextField(TEXT_SIZE - 10);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds all the components to the form page panel;
     */
    private void addComponentsToFormPage() {
        formPage.add(dateLabel);

        formPage.add(titleLabel);
        formPage.add(titleText);

        formPage.add(contentLabel);
        formPage.add(contentText);
        
        formPage.add(moodLabel);
        formPage.add(moodText);

        formPage.add(addEntryButton);
        formPage.add(viewEntriesButton);
        // formPage.add(allTitle);

        contentText.requestFocusInWindow();
        update();
    }

    /*
     * MODIFIES: this
     * EFFECTS: updates the journal user interface based on the event that occurred;
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == loadButton) {
            loadJournal();
        } else if (source == saveButton) {
            saveJournal();
        } else if (source == viewFormButton) {
            formPage.setVisible(true);
            homePage.setVisible(false);
            logPage.setVisible(false);
            checkViewFormButton();
        } else if (source == viewEntriesButton) {
            logPage.setVisible(true);
            homePage.setVisible(false);
            formPage.setVisible(false);
            checkViewEntriesButton();
        } else if (source == addEntryButton) {
            checkAddEntryButton();
        } else if (source == removeEntryButton) {
            isRemoveEntryButtonClicked = true;
        } else {
            System.out.println("Sorry, there's no corresponding action for this event.");
            System.out.println("Try checking the JournalUI.actionPerformed() method!");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: checks if the viewFormButton was clicked and displays the entry
     * -------- input form of the journal if it was;
     */
    private void checkViewFormButton() {
        update();
    }

    /*
     * MODIFIES: this
     * EFFECTS: checks if the viewEntriesButton was clicked and displays the
     * -------- journal's entire entry log if it was;
     */
    private void checkViewEntriesButton() {
        // TOP: button to go to form page
        buttonPanel.add(viewFormButton);
        buttonPanel.add(removeEntryButton);
        logPage.add(buttonPanel, BorderLayout.PAGE_START);
        
        // MIDDLE: fields to display selected entry
        initFormTexts();
        JPanel entryPanel = new JPanel();
        SpringLayout springLayout = new SpringLayout();
        entryPanel.setLayout(springLayout);
        populateEntryPanelOnLogPage(entryPanel);
        setEntryPanelConstraints(entryPanel, springLayout);
        logPage.add(entryPanel, BorderLayout.CENTER);
        
        // BOTTOM: list of entries to select
        JList<Entry> entriesList = new JList<>(journal.getEntries().toArray(new Entry[0]));
        logPage.add(entriesList, BorderLayout.PAGE_END);

        // when entry is selected, display entry info
        watchSelectionList(entriesList);

        update();
    }

    /*
     * MODIFIES: this
     * EFFECTS: watches the selection list of entries and updates the journal
     * -------- user interface accordingly;
     */
    private void watchSelectionList(JList<Entry> entriesList) {
        entriesList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                if (isRemoveEntryButtonClicked) {
                    journal.removeEntry(entriesList.getSelectedValue());
                    update();
                    isRemoveEntryButtonClicked = false;
                }
                Entry selectedEntry = entriesList.getSelectedValue();
                initFormLabels();
                dateLabel.setText("Date: " + selectedEntry.getDate());
                titleText.setText(selectedEntry.getTitle());
                contentText.setText(selectedEntry.getContent());
                moodText.setText(selectedEntry.getMood());
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: populates the entry panel of the journal user interface with the
     * -------- necessary labels and text fields;
     */
    private void populateEntryPanelOnLogPage(JPanel entryPanel) {
        entryPanel.add(dateLabel);
        dateLabel.setText("Date: " + LocalDate.now().toString());
        entryPanel.add(titleLabel);
        entryPanel.add(titleText);
        entryPanel.add(contentLabel);
        entryPanel.add(contentText);
        entryPanel.add(moodLabel);
        entryPanel.add(moodText);
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets the constraints for the entry panel of the journal user interface;
     */
    private void setEntryPanelConstraints(JPanel entryPanel, SpringLayout springLayout) {
        springLayout.putConstraint(SpringLayout.WEST, dateLabel, 5, SpringLayout.WEST, entryPanel);
        springLayout.putConstraint(SpringLayout.NORTH, dateLabel, 5, SpringLayout.NORTH, entryPanel);
        springLayout.putConstraint(SpringLayout.WEST, titleLabel, 5, SpringLayout.WEST, entryPanel);
        springLayout.putConstraint(SpringLayout.NORTH, titleLabel, 5, SpringLayout.SOUTH, dateLabel);
        springLayout.putConstraint(SpringLayout.WEST, titleText, 5, SpringLayout.EAST, titleLabel);
        springLayout.putConstraint(SpringLayout.NORTH, titleText, 5, SpringLayout.SOUTH, dateLabel);
        springLayout.putConstraint(SpringLayout.WEST, contentLabel, 5, SpringLayout.WEST, entryPanel);
        springLayout.putConstraint(SpringLayout.NORTH, contentLabel, 5, SpringLayout.SOUTH, titleLabel);
        springLayout.putConstraint(SpringLayout.WEST, contentText, 5, SpringLayout.EAST, contentLabel);
        springLayout.putConstraint(SpringLayout.NORTH, contentText, 5, SpringLayout.SOUTH, titleLabel);
        springLayout.putConstraint(SpringLayout.WEST, moodLabel, 5, SpringLayout.WEST, entryPanel);
        springLayout.putConstraint(SpringLayout.NORTH, moodLabel, 35, SpringLayout.SOUTH, contentLabel);
        springLayout.putConstraint(SpringLayout.WEST, moodText, 5, SpringLayout.EAST, moodLabel);
        springLayout.putConstraint(SpringLayout.NORTH, moodText, 35, SpringLayout.SOUTH, contentLabel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: checks if the add entry button was clicked and adds the entry to the
     * -------- journal if it was;
     */
    private void checkAddEntryButton() {
        title = titleText.getText();
        content = contentText.getText();
        mood = moodText.getText();

        entry = new Entry(content);
        entry.setTitle(title);
        entry.setMood(mood);

        journal.addEntry(entry);
        clearFormFields();
        update();
    }

    /*
     * MODIFIES: this
     * EFFECTS: updates the journal user interface graphics;
     */
    private void update() { // TODO: glitchy
        update(getGraphics());
        this.pack();
        setSize(APP_WIDTH, APP_HEIGHT);
        // formPage.setVisible(true);
    }

    /*
     * MODIFIES: this
     * EFFECTS: clears all the text fields of the journal user interface;
     */
    private void clearFormFields() {
        titleText.setText("");
        contentText.setText("");
        moodText.setText("");
    }

    /*
     * EFFECTS: saves the journal to file;
     */
    private Boolean saveJournal() {
        try {
            jsonWriter.open();
            jsonWriter.write(journal);
            jsonWriter.close();
            System.out.println("Saved journal to " + JSON_STORE + "! \nSQUEAK");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
        return true;
    }

    /*
     * MODIFIES: this;
     * EFFECTS: loads journal from file;
     */
    private Boolean loadJournal() {
        try {
            journal = jsonReader.read();
            System.out.println("Loaded journal from " + JSON_STORE + "! \nSQUEAK");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        return true;
    }
}
