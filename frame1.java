
import javax.swing.*;
import java.awt.*;

public class frame1 extends JFrame {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hey there!");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set background color
        frame.getContentPane().setBackground(new Color(236, 240, 241));
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        // Welcome label
        JLabel label = new JLabel("Welcome to Student Assistant ChatBot");
        label.setFont(new Font("Arial", Font.BOLD, 26));
        label.setForeground(new Color(34, 49, 63));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        // Buttons
        JButton loginBtn = createStyledButton("Login", new Color(52, 152, 219));
        JButton signupBtn = createStyledButton("Sign up", new Color(46, 204, 113));

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(236, 240, 241));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(loginBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(signupBtn);

        // Add components to frame
        frame.add(label);
        frame.add(buttonPanel);

        // Button actions
        loginBtn.addActionListener(e -> {
            new login(frame).setVisible(true);
            frame.setVisible(false);
        });

        signupBtn.addActionListener(e -> {
            new signup(frame).setVisible(true);
            frame.setVisible(false);
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Method to create attractive buttons
    private static JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(200, 50));
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
}


