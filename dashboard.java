import javax.swing.*;
import java.awt.*;

public class dashboard extends JFrame {

    int studentId; // ✅ store logged in user id

    public dashboard(int studentId) {
        super("Dashboard");

        this.studentId = studentId;

        // Welcome label with stylish font
        JLabel welcomeLabel = new JLabel("Dashboard", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        welcomeLabel.setForeground(new Color(34, 49, 63));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));

        // Buttons
        JButton Button1 = createStyledButton("Chat with bot");
        JButton Button2 = createStyledButton("Task manager");
        JButton Button3 = createStyledButton("Upcoming reminders");
        JButton Button4 = createStyledButton("Logout");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(236, 240, 241)); // light background

        JButton[] buttons = { Button1, Button2, Button3, Button4 };
        for (JButton button : buttons) {
            button.setAlignmentX(Component.CENTER_ALIGNMENT); // center align
            panel.add(button);
            panel.add(Box.createRigidArea(new Dimension(0, 15))); // spacing between buttons
        }

        setLayout(new BorderLayout());
        add(welcomeLabel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        getContentPane().setBackground(new Color(236, 240, 241)); // overall background

        // Button actions
        Button1.addActionListener(e -> {
            new bot(studentId).setVisible(true);
            dispose();
        });

        Button2.addActionListener(e -> {
            new task(studentId).setVisible(true);
            dispose();
        });

        Button3.addActionListener(e -> {
            new reminder(studentId).setVisible(true);
            dispose();
        });

        Button4.addActionListener(e -> {
            new frame1().setVisible(true);
            dispose();
        });

        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setBackground(new Color(52, 152, 219));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Fixed size for all buttons
        button.setPreferredSize(new Dimension(250, 50));
        button.setMaximumSize(new Dimension(250, 50));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(41, 128, 185));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(52, 152, 219));
            }
        });

        return button;
    }


    // Main method for testing
    public static void main(String[] args) {
        new dashboard(1); // test with studentId = 1
    }
}
