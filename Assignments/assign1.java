package pattern;

import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class assign1 extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField nameField, emailField;
    private JRadioButton maleRadio, femaleRadio, otherRadio;
    private JComboBox<String> courseCombo;
    private JCheckBox readingCheck, travelingCheck, gamingCheck;
    private JButton submitButton, resetButton;
    private ButtonGroup genderGroup;

    public assign1() {
        setTitle("Student Registration Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        // Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        add(nameField, gbc);

        // Email
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        add(emailField, gbc);

        // Gender
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Gender:"), gbc);

        gbc.gridx = 1;
        genderGroup = new ButtonGroup();
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        otherRadio = new JRadioButton("Other");
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderGroup.add(otherRadio);
        JPanel genderPanel = new JPanel(new FlowLayout());
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        genderPanel.add(otherRadio);
        add(genderPanel, gbc);

        // Course
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Course:"), gbc);

        gbc.gridx = 1;
        String[] courses = {"B.Tech", "B.Sc", "B.Com", "etc"};
        courseCombo = new JComboBox<>(courses);
        add(courseCombo, gbc);

        // Hobbies
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Hobbies:"), gbc);

        gbc.gridx = 1;
        readingCheck = new JCheckBox("Reading");
        travelingCheck = new JCheckBox("Traveling");
        gamingCheck = new JCheckBox("Gaming");
        JPanel hobbiesPanel = new JPanel(new FlowLayout());
        hobbiesPanel.add(readingCheck);
        hobbiesPanel.add(travelingCheck);
        hobbiesPanel.add(gamingCheck);
        add(hobbiesPanel, gbc);

        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // Span across both columns
        gbc.anchor = GridBagConstraints.CENTER;
        submitButton = new JButton("Submit");
        resetButton = new JButton("Reset");
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(submitButton);
        buttonPanel.add(resetButton);
        add(buttonPanel, gbc);

        // Action listeners
        submitButton.addActionListener(e -> validateAndShowData());
        resetButton.addActionListener(e -> clearFields());

        pack();
        setLocationRelativeTo(null);
    }

    private void validateAndShowData() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and Email fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String gender = maleRadio.isSelected() ? "Male" : femaleRadio.isSelected() ? "Female" : "Other";
        String course = (String) courseCombo.getSelectedItem();
        String hobbies = "";
        if (readingCheck.isSelected()) hobbies += "Reading, ";
        if (travelingCheck.isSelected()) hobbies += "Traveling, ";
        if (gamingCheck.isSelected()) hobbies += "Gaming, ";
        if (!hobbies.isEmpty()) hobbies = hobbies.substring(0, hobbies.length() - 2);

        String message = String.format("Name: %s\nEmail: %s\nGender: %s\nCourse: %s\nHobbies: %s",
                name, email, gender, course, hobbies.isEmpty() ? "None" : hobbies);
        JOptionPane.showMessageDialog(this, message, "Submitted Data", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        genderGroup.clearSelection();
        courseCombo.setSelectedIndex(0);
        readingCheck.setSelected(false);
        travelingCheck.setSelected(false);
        gamingCheck.setSelected(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            assign1 form = new assign1();
            form.setVisible(true);
        });
    }
}
