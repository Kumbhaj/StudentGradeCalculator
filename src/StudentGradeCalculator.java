import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentGradeCalculator extends JFrame implements ActionListener {
    private JTextField gradeCountField;
    private JPanel inputPanel;
    private JButton calculateButton;
    private JLabel resultLabel;

    private JTextField[] gradeFields;

    public StudentGradeCalculator() {
        setTitle("Student Grade Calculator");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Enter number of grades:"));
        gradeCountField = new JTextField(5);
        topPanel.add(gradeCountField);

        JButton generateFieldsButton = new JButton("Enter Grades");
        generateFieldsButton.addActionListener(e -> generateGradeFields());
        topPanel.add(generateFieldsButton);

        add(topPanel, BorderLayout.NORTH);

        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(inputPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        calculateButton = new JButton("Calculate Average");
        calculateButton.addActionListener(this);
        bottomPanel.add(calculateButton, BorderLayout.NORTH);

        resultLabel = new JLabel("Average: ");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(resultLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void generateGradeFields() {
        inputPanel.removeAll();

        try {
            int count = Integer.parseInt(gradeCountField.getText());
            if (count <= 0) {
                throw new NumberFormatException();
            }

            gradeFields = new JTextField[count];

            for (int i = 0; i < count; i++) {
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                panel.add(new JLabel("Grade " + (i + 1) + ":"));
                gradeFields[i] = new JTextField(5);
                panel.add(gradeFields[i]);
                inputPanel.add(panel);
            }

            inputPanel.revalidate();
            inputPanel.repaint();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid positive number!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gradeFields == null || gradeFields.length == 0) {
            JOptionPane.showMessageDialog(this, "Please enter the number of grades and click 'Enter Grades' first!", "Missing Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double sum = 0;
            for (JTextField gradeField : gradeFields) {
                double grade = Double.parseDouble(gradeField.getText());
                if (grade < 0 || grade > 100) {
                    throw new IllegalArgumentException("Grade must be between 0 and 100.");
                }
                sum += grade;
            }

            double average = sum / gradeFields.length;
            resultLabel.setText(String.format("Average: %.2f", average));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric grades!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid Grade", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentGradeCalculator calculator = new StudentGradeCalculator();
            calculator.setVisible(true);
        });
    }
}
