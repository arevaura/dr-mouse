package ui.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import javax.swing.DefaultListModel;
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
import model.Event;
import model.EventLog;
import model.Journal;
import persistence.JsonReader;
import persistence.JsonWriter;

/*
 * EFFECTS: Represents the graphical user interface for the journal application; 
 * -------- allows user to input and view journal entries;
 */
public class JournalUI extends JFrame implements ActionListener, WindowListener {

    // ==========--FIELDS--==========

    private static final String JSON_STORE = "./data/journal.json";
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);

    private EventLog eventLog = EventLog.getInstance();

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
    private JButton modifyEntryButton;
    private JButton removeEntryButton;
    private boolean modifyEntryButtonClicked;
    private boolean removeEntryButtonClicked;
    private JButton viewEntriesButton;

    private JPanel homePage; // page with just buttons, redirect page BOXLAYOUT
    private JPanel formPage; // page for adding new entry BORDERLAYOUT
    private JPanel buttonPanel; // section for showing button options FLOWLAYOUT
    private JPanel logPage; // page to see log entries BORDERLAYOUT

    static final int TEXT_SIZE = 50;
    static final int APP_WIDTH = 740;
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
        addWindowListener(this);
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
        homePage.setLayout(new BorderLayout(0, 30));
        homePage.setBackground(Color.WHITE);

        JPanel centerContentsPanel = new JPanel();
        centerContentsPanel.setLayout(new BorderLayout());
        ImageIcon image = new ImageIcon("./graphics/confusedmouse.jpg");
        centerContentsPanel.add(new JLabel(image), BorderLayout.CENTER);
        // homePage.add(Box.createRigidArea(new Dimension(0, 15)));

        centerContentsPanel.add(new JLabel("Welcome to Dr. Mouse!"), BorderLayout.PAGE_START);
        // homePage.add(Box.createRigidArea(new Dimension(0, 30)));
        // buttonPanel.removeAll();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(viewFormButton);
        // homePage.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonPanel.add(viewEntriesButton);
        // homePage.add(Box.createRigidArea(new Dimension(0, 30)));
        buttonPanel.add(loadButton);
        centerContentsPanel.add(buttonPanel, BorderLayout.PAGE_END);
        homePage.add(centerContentsPanel, BorderLayout.CENTER);

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
        SpringLayout formSpringLayout = new SpringLayout();
        JPanel form = new JPanel();
        form.setLayout(formSpringLayout); // TODO: problem is only texts show after first entry
        // form.setVisible(true);
        formPage.add(form, BorderLayout.CENTER);

        populateFormPageWithFields(form);
        setFormPageConstraints(formSpringLayout, form);

        buttonPanel.removeAll();
        buttonPanel.add(addEntryButton);
        buttonPanel.add(viewEntriesButton);
        // buttonPanel.setVisible(true);
        formPage.add(buttonPanel, BorderLayout.PAGE_END);
        update();

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

        modifyEntryButton = new JButton("Edit Entry");
        modifyEntryButton.addActionListener(this);
        modifyEntryButtonClicked = false;

        removeEntryButton = new JButton("Delete Entry");
        removeEntryButton.addActionListener(this);
        removeEntryButtonClicked = false;

        viewEntriesButton = new JButton("View Entries");
        viewEntriesButton.addActionListener(this);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes all the labels of the journal user interface;
     */
    private void initFormLabels() {
        dateLabel = new JLabel("Date: " + LocalDate.now().toString());
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
        contentText = new JTextArea("REQUIRED FIELD: Click to edit.", 3, 58);
        contentText.setWrapStyleWord(true); // makes sure box doesn't shrink when no text present
        contentText.setLineWrap(true); // makes sure text wraps around
        moodText = new JTextField(TEXT_SIZE - 10);
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets the constraints for the form page of the journal user
     * interface;
     */
    private void setFormPageConstraints(SpringLayout formSpringLayout, JPanel form) {
        formSpringLayout.putConstraint(SpringLayout.WEST, dateLabel, 5, SpringLayout.WEST, form);
        formSpringLayout.putConstraint(SpringLayout.NORTH, dateLabel, 5, SpringLayout.NORTH, form);
        formSpringLayout.putConstraint(SpringLayout.WEST, titleLabel, 5, SpringLayout.WEST, form);
        formSpringLayout.putConstraint(SpringLayout.NORTH, titleLabel, 5, SpringLayout.SOUTH, dateLabel);
        formSpringLayout.putConstraint(SpringLayout.WEST, titleText, 5, SpringLayout.EAST, titleLabel);
        formSpringLayout.putConstraint(SpringLayout.NORTH, titleText, 5, SpringLayout.SOUTH, dateLabel);
        formSpringLayout.putConstraint(SpringLayout.WEST, contentLabel, 5, SpringLayout.WEST, form);
        formSpringLayout.putConstraint(SpringLayout.NORTH, contentLabel, 5, SpringLayout.SOUTH, titleLabel);
        formSpringLayout.putConstraint(SpringLayout.WEST, contentText, 5, SpringLayout.EAST, contentLabel);
        formSpringLayout.putConstraint(SpringLayout.NORTH, contentText, 5, SpringLayout.SOUTH, titleLabel);
        formSpringLayout.putConstraint(SpringLayout.WEST, moodLabel, 5, SpringLayout.WEST, form);
        formSpringLayout.putConstraint(SpringLayout.NORTH, moodLabel, 35, SpringLayout.SOUTH, contentLabel);
        formSpringLayout.putConstraint(SpringLayout.WEST, moodText, 5, SpringLayout.EAST, moodLabel);
        formSpringLayout.putConstraint(SpringLayout.NORTH, moodText, 35, SpringLayout.SOUTH, contentLabel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: populates the form page of the journal user interface with the
     * necessary fields;
     */
    private void populateFormPageWithFields(JPanel form) {
        form.add(dateLabel);
        form.add(titleLabel);
        form.add(titleText);
        form.add(contentLabel);
        form.add(contentText);
        contentText.requestFocusInWindow();
        form.add(moodLabel);
        form.add(moodText);
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
            checkViewFormButton();
            formPage.setVisible(true);
            homePage.setVisible(false);
            logPage.setVisible(false);
        } else if (source == viewEntriesButton) {
            checkViewEntriesButton();
            logPage.setVisible(true);
            homePage.setVisible(false);
            formPage.setVisible(false);
        } else if (source == addEntryButton) {
            checkAddEntryButton();
        } else if (source == modifyEntryButton) {
            modifyEntryButtonClicked = true;
        } else if (source == removeEntryButton) {
            removeEntryButtonClicked = true;
        }
        // } else {
        // System.out.println("Sorry, there's no corresponding action for this event.");
        // System.out.println("Try checking the JournalUI.actionPerformed() method!");
        // }
    }

    /*
     * MODIFIES: this
     * EFFECTS: checks if the viewFormButton was clicked and displays the entry
     * -------- input form of the journal if it was;
     */
    private void checkViewFormButton() {
        buttonPanel.removeAll();
        buttonPanel.add(addEntryButton);
        buttonPanel.add(viewEntriesButton);
        buttonPanel.setVisible(true);
        formPage.add(buttonPanel, BorderLayout.PAGE_END);
        update();
    }

    /*
     * MODIFIES: this
     * EFFECTS: checks if the viewEntriesButton was clicked and displays the
     * -------- journal's entire entry log if it was;
     */
    private void checkViewEntriesButton() {
        // TOP: button to go to form page
        buttonPanel.removeAll();
        buttonPanel.add(viewFormButton);
        // buttonPanel.add(modifyEntryButton);
        buttonPanel.add(removeEntryButton);
        buttonPanel.add(saveButton);
        logPage.add(buttonPanel, BorderLayout.PAGE_START);

        // MIDDLE: fields to display selected entry
        initFormTexts();
        JPanel entryPanel = new JPanel();
        SpringLayout springLayout = new SpringLayout();
        entryPanel.setLayout(springLayout);
        populateEntryPanelOnLogPage(entryPanel);
        setEntryPanelConstraints(entryPanel, springLayout);
        logPage.add(entryPanel, BorderLayout.CENTER);

        // SIDE: list of entries to select
        
        DefaultListModel<Entry> listModel = new DefaultListModel<>(); // TODO
        
        for (Entry entry : journal.getEntries()) {
            listModel.addElement(entry);
        }
        // entriesList.setModel(listModel);
        JList<Entry> entriesList = new JList<>(listModel);

        entriesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        entriesList.setBackground(Color.LIGHT_GRAY);
        logPage.add(entriesList, BorderLayout.LINE_START);

        // when entry is selected, display entry info
        watchSelectionList(entriesList, listModel);

        update();
    }

    /*
     * MODIFIES: this
     * EFFECTS: watches the selection list of entries and updates the journal
     * -------- user interface accordingly;
     */
    private void watchSelectionList(JList<Entry> entriesList, DefaultListModel<Entry> listModel) {
        entriesList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(javax.swing.event.ListSelectionEvent e) {
                // Check if the event is final to prevent double firing
                if (!e.getValueIsAdjusting()) {
                    if (removeEntryButtonClicked) {
                        journal.removeEntry(entriesList.getSelectedValue());
                        // System.out.println(journal.getEntries());
                        // System.out.println(listModel.getSize());
                        // listModel.remove(entriesList.getSelectedIndex());
                        entriesList.revalidate();
                        entriesList.repaint();
                        // ((DefaultListModel<Entry>)entriesList.getModel()).removeElement(selectedEntry);
                        // // TODO
                        update();
                        removeEntryButtonClicked = false;
                    }
                } else {
                    Entry selectedEntry = entriesList.getSelectedValue();
                    if (selectedEntry != null) { // Check if an entry is selected
                        initFormLabels();
                        dateLabel.setText("Date: " + selectedEntry.getDate());
                        titleText.setText(selectedEntry.getTitle());
                        contentText.setText(selectedEntry.getContent());
                        moodText.setText(selectedEntry.getMood());
                        // if (modifyEntryButtonClicked) {
                        // selectedEntry.setTitle(titleText.getText());
                        // selectedEntry.setContent(contentText.getText());
                        // selectedEntry.setMood(moodText.getText());
                        // modifyEntryButtonClicked = false;
                        // }
                    }
                }
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
     * EFFECTS: sets constraints for entry panel of the journal user interface;
     */
    private void setEntryPanelConstraints(JPanel entryPanel, SpringLayout springLayout) {
        setFormPageConstraints(springLayout, entryPanel);
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
        revalidate(); // TODO: remove these if not needed
        repaint();
        this.pack();
        setSize(APP_WIDTH, APP_HEIGHT);
        // setVisible(true);
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

    @Override
    // MODIFIES: this
    // EFFECTS: clears event log when application opens
    public void windowOpened(WindowEvent e) {
        eventLog.clear();
        System.out.println("\n<< New journal session started... >>\n");
    }

    @Override
    // MODIFIES: this
    // EFFECTS: prints out event log when application closes
    public void windowClosing(WindowEvent e) {
        System.out.println("Event log for current journal session printed below:\n");
        for (Event event : eventLog) {
            System.out.println("> " + event + "\n");
        }
        System.out.println("<< Current journal session terminated. >>\n");
    }

    @Override
    // MODIFIES: this
    // EFFECTS: does nothing but overrides windowClosing() to satisfy WindowListener
    public void windowClosed(WindowEvent e) {
    }

    @Override
    // MODIFIES: this
    // EFFECTS: does nothing but overrides windowClosing() to satisfy WindowListener
    public void windowIconified(WindowEvent e) {
    }

    @Override
    // MODIFIES: this
    // EFFECTS: does nothing but overrides windowClosing() to satisfy WindowListener
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    // MODIFIES: this
    // EFFECTS: does nothing but overrides windowClosing() to satisfy WindowListener
    public void windowActivated(WindowEvent e) {
    }

    @Override
    // MODIFIES: this
    // EFFECTS: does nothing but overrides windowClosing() to satisfy WindowListener
    public void windowDeactivated(WindowEvent e) {
    }
}
