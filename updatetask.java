import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class updatetask extends JFrame {

    int studentId;

    public updatetask(int studentId) {
        super("Update Task");
        this.studentId = studentId;

        // Background & layout
        getContentPane().setBackground(new Color(236, 240, 241));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setBorderPadding();

        // Title
        JLabel titleLabel = new JLabel("Update Task");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(34, 49, 63));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel);

        // Input fields with labels
        add(createLabel("Old Task Title:"));
        JTextField oldTitle = createTextField("Old Task Title");
        add(oldTitle);
        add(Box.createRigidArea(new Dimension(0, 10)));

        add(createLabel("New Task Title:"));
        JTextField newTitle = createTextField("New Title");
        add(newTitle);
        add(Box.createRigidArea(new Dimension(0, 10)));

        add(createLabel("Description:"));
        JTextField descField = createTextField("Description");
        add(descField);
        add(Box.createRigidArea(new Dimension(0, 10)));

        add(createLabel("Due Date (YYYY-MM-DD):"));
        JTextField dueDate = createTextField("Due Date (YYYY-MM-DD)");
        add(dueDate);
        add(Box.createRigidArea(new Dimension(0, 20)));

        // Buttons with labels
        JButton updateBtn = createStyledButton("Update Task", new Color(52, 152, 219));
        JButton backBtn = createStyledButton("← Back", new Color(192, 57, 43));

        add(updateBtn);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(backBtn);
        add(Box.createRigidArea(new Dimension(0, 20)));

        // Actions
        updateBtn.addActionListener(e -> updateTaskInDB(studentId,
                oldTitle.getText(),
                newTitle.getText(),
                descField.getText(),
                dueDate.getText()));

        backBtn.addActionListener(e -> {
            new task(studentId).setVisible(true);
            dispose();
        });

        // Frame settings
        setSize(400, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Padding for BoxLayout
    private void setBorderPadding() {
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    // Create JLabel
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(new Color(34, 49, 63));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        return label;
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

    // Database update method
    private void updateTaskInDB(int studentId, String oldTitle, String newTitle, String desc, String date) {
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
            find.setString(2, oldTitle);
            ResultSet rs = find.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "❌ Task not found!");
                con.close();
                return;
            }

            int taskId = rs.getInt("task_id");

            // Update task
            String updateSql = "UPDATE task_manager SET task_title=?, task_description=?, due_date=? WHERE task_id=?";
            PreparedStatement pst = con.prepareStatement(updateSql);
            pst.setString(1, newTitle);
            pst.setString(2, desc);
            pst.setDate(3, java.sql.Date.valueOf(date));
            pst.setInt(4, taskId);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "✅ Task Updated Successfully!");
                new task(studentId).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "❌ Update Failed!");
            }

            con.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Error: " + e.getMessage());
        }
    }

    // Main for testing
    public static void main(String[] args) {
        new updatetask(1);
    }
}
