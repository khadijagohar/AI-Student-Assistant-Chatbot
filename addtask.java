import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class addtask extends JFrame {

    int studentId;

    public addtask(int studentId) {
        super("Add Task");
        this.studentId = studentId;

        // ---------- Frame settings ----------
        getContentPane().setBackground(new Color(236, 240, 241));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setBorderPadding();

        // ---------- Title ----------
        JLabel titleLabel = new JLabel("Add New Task");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(34, 49, 63));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel);

        // ---------- Input Fields ----------
        add(createLabel("Task Title:"));
        JTextField txtTitle = createTextField("Enter task title");
        add(txtTitle);
        add(Box.createRigidArea(new Dimension(0, 10)));

        add(createLabel("Description:"));
        JTextField txtDesc = createTextField("Enter description");
        add(txtDesc);
        add(Box.createRigidArea(new Dimension(0, 10)));

        add(createLabel("Due Date (YYYY-MM-DD):"));
        JTextField txtDue = createTextField("YYYY-MM-DD");
        add(txtDue);
        add(Box.createRigidArea(new Dimension(0, 10)));

        add(createLabel("Status:"));
        JTextField txtStatus = createTextField("Enter status");
        add(txtStatus);
        add(Box.createRigidArea(new Dimension(0, 20)));

        // ---------- Buttons ----------
        JButton addBtn = createStyledButton("Add Task", new Color(52, 152, 219));
        JButton backBtn = createStyledButton("← Back", new Color(192, 57, 43));
        add(addBtn);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(backBtn);
        add(Box.createRigidArea(new Dimension(0, 20)));

        // ---------- Button Actions ----------
        addBtn.addActionListener(e -> addTaskToDB(
                txtTitle.getText(),
                txtDesc.getText(),
                txtDue.getText(),
                txtStatus.getText()
        ));

        backBtn.addActionListener(e -> {
            new task(studentId).setVisible(true);
            dispose();
        });

        // ---------- Frame Settings ----------
        setSize(450, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // ---------- Helper Methods ----------
    private void setBorderPadding() {
        ((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(new Color(34, 49, 63));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        return label;
    }

    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(350, 35));
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        field.setToolTipText(placeholder);
        return field;
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(200, 40));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

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

    // ---------- Database Method ----------
    private void addTaskToDB(String title, String desc, String dueDate, String status) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe",
                    "system",
                    "123"
            );

            String sql = "INSERT INTO task_manager (student_id, task_title, task_description, due_date, task_status) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, studentId);
            pst.setString(2, title);
            pst.setString(3, desc);
            pst.setDate(4, java.sql.Date.valueOf(dueDate));
            pst.setString(5, status);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "✅ Task Added Successfully!");
                new dashboard(studentId).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "❌ Task could not be added!");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "❌ Error: " + e.getMessage());
        }
    }

    // ---------- Main ----------
    public static void main(String[] args) {
        new addtask(1);
    }
}



   
