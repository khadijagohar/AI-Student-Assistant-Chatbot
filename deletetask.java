import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class deletetask extends JFrame {

    int studentId;

    public deletetask(int studentId) {
        super("Delete Task");
        this.studentId = studentId;

        // Background & layout
        getContentPane().setBackground(new Color(236, 240, 241));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setBorderPadding();

        // Title
        JLabel titleLabel = new JLabel("Delete Task");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(34, 49, 63));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel);

        // Input field
        JTextField titleField = createTextField("Task Title");

        // Buttons
        JButton deleteBtn = createStyledButton("Delete Task", new Color(231, 76, 60));
        JButton backBtn = createStyledButton("← Back", new Color(192, 57, 43));

        // Add components with spacing
        add(titleField);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(deleteBtn);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(backBtn);
        add(Box.createRigidArea(new Dimension(0, 20)));

        // Actions
        deleteBtn.addActionListener(e -> deleteTaskFromDB(studentId, titleField.getText()));
        backBtn.addActionListener(e -> {
            new task(studentId).setVisible(true);
            dispose();
        });

        // Frame settings
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Padding for BoxLayout
    private void setBorderPadding() {
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    // TextField with placeholder
    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(300, 35));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        field.setToolTipText(placeholder);
        return field;
    }

    // Styled button
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(250, 40));
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

    // Delete task from DB
    private void deleteTaskFromDB(int studentId, String title) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe",
                    "system",
                    "123"
            );

            // Find task_id
            String findSql = "SELECT task_id FROM task_manager WHERE student_id=? AND task_title=?";
            PreparedStatement find = con.prepareStatement(findSql);
            find.setInt(1, studentId);
            find.setString(2, title);
            ResultSet rs = find.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "❌ Task not found!");
                con.close();
                return;
            }

            int taskId = rs.getInt("task_id");

            // Delete task
            String deleteSql = "DELETE FROM task_manager WHERE task_id=?";
            PreparedStatement pst = con.prepareStatement(deleteSql);
            pst.setInt(1, taskId);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "🗑️ Task Deleted Successfully!");
                new task(studentId).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "❌ Delete Failed!");
            }

            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Error: " + e.getMessage());
        }
    }

    // Main for testing
    public static void main(String[] args) {
        new deletetask(1);
    }
}

