import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class task extends JFrame {

    int studentId;

    public task(int studentId) {
        super("Task Manager");
        this.studentId = studentId;

        // Set background
        getContentPane().setBackground(new Color(236, 240, 241));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Title label
        JLabel titleLabel = new JLabel("Task Manager");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(34, 49, 63));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(titleLabel);

        // Buttons
        JButton addtask = createStyledButton("Add Task", new Color(52, 152, 219));
        JButton deletetask = createStyledButton("Delete Task", new Color(231, 76, 60));
        JButton updatetask = createStyledButton("Update Task", new Color(52, 152, 219));
        JButton backbtn = createStyledButton("Back to Dashboard", new Color(41, 128, 185));

        // Add spacing and buttons
        add(addtask);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(deletetask);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(updatetask);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(backbtn);
        add(Box.createRigidArea(new Dimension(0, 20)));

        // Button actions
        addtask.addActionListener(e -> {
            new addtask(studentId).setVisible(true);
            dispose();
        });

        deletetask.addActionListener(e -> {
            new deletetask(studentId).setVisible(true);
            dispose();
        });

        updatetask.addActionListener(e -> {
            new updatetask(studentId).setVisible(true);
            dispose();
        });

        backbtn.addActionListener(e -> {
            new dashboard(studentId).setVisible(true);
            dispose();
        });

        // Frame settings
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Method to style buttons
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(250, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    // Main for testing
    public static void main(String[] args) {
        new task(1);
    }
}
