import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class signup extends JFrame {

    private JFrame mainFrame; // reference to existing frame1

    public signup(JFrame mainFrame) {
        super("Sign Up");
        this.mainFrame = mainFrame;

        // Set background color
        getContentPane().setBackground(new Color(236, 240, 241));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Title label
        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(34, 49, 63));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(titleLabel);

        // Username
        JLabel userLabel = new JLabel("Choose Username");
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JTextField userField = new JTextField(20);
        userField.setMaximumSize(new Dimension(250, 35));
        userField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Password
        JLabel passLabel = new JLabel("Choose Password");
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPasswordField passField = new JPasswordField(20);
        passField.setMaximumSize(new Dimension(250, 35));
        passField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Sign Up button
        JButton signupButton = createStyledButton("Sign Up", new Color(52, 152, 219));

        // Back button at the bottom
        JButton backBtn = createStyledButton("← Back", new Color(192, 57, 43));

        // Add components with spacing
        add(userLabel);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(userField);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(passLabel);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(passField);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(signupButton);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(backBtn);
        add(Box.createRigidArea(new Dimension(0, 20)));

        // Signup action
        signupButton.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection(
                        "jdbc:oracle:thin:@localhost:1521:xe",
                        "system",
                        "123"
                );

                String sql = "INSERT INTO studentbot_users (username, password) VALUES (?, ?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, user);
                pst.setString(2, pass);

                int rows = pst.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(null, "Signup Successful!");
                    if (mainFrame != null) mainFrame.setVisible(true);
                    dispose();
                }

                pst.close();
                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        // Back action
        backBtn.addActionListener(e -> {
            if (mainFrame != null) mainFrame.setVisible(true);
            dispose();
        });

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
        button.setMaximumSize(new Dimension(200, 40));
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
        new signup(null);
    }
}

