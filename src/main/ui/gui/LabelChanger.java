package ui.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; // an interface

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LabelChanger extends JFrame implements ActionListener {
    private JLabel label;
    private JTextField field;

    public LabelChanger() {
        super("The title");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(400, 90));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        setLayout(new FlowLayout());
        JButton btn = new JButton("Change");
        btn.setActionCommand("myButton");
        btn.addActionListener(this); // Sets "this" object as an action listener for btn
                                     // so that when the btn is clicked,
                                     // this.actionPerformed(ActionEvent e) will be called.
                                     // You could also set a different object, if you wanted
                                     // a different object to respond to the button click
        label = new JLabel("flag");
        field = new JTextField(5);
        add(field);
        add(btn);
        add(label);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // This is the method that is called when the the JButton btn is clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("myButton")) {
            label.setText(field.getText());
        }
    }

    public static void main(String[] args) {
        new LabelChanger();
    }
}