import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class bot extends JFrame {

    private JTextArea promptTextArea;
    private JButton sendButton;
    private JTextArea responseTextArea;
    private JButton backButton;
    private GeminiAPI api;
    int studentId;

    // SINGLE WORKING CONSTRUCTOR
    public bot(int studentId) {

        this.studentId = studentId;
        api = new GeminiAPI();

        setTitle("Chat with AI Student Bot");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // MAIN PANEL WITH GRADIENT
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0,
                        new Color(102, 126, 234),
                        getWidth(), getHeight(),
                        new Color(118, 75, 162));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        mainPanel.setLayout(new GridBagLayout());
        add(mainPanel);

        // MAIN CONTAINER
        RoundedPanel container = new RoundedPanel();
        container.setLayout(new BorderLayout(20, 20));
        container.setPreferredSize(new Dimension(700, 500));
        container.setBorder(new EmptyBorder(30, 30, 30, 30));

        // BACK BUTTON
        backButton = new JButton("← Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.setBackground(new Color(118, 75, 162));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(100, 40));

        backButton.addActionListener(e -> {
            dispose();
            new dashboard(studentId).setVisible(true); // return to dashboard with same ID
        });

        container.add(backButton, BorderLayout.WEST);

        // TITLE
        JLabel title = new JLabel("Chat with AI Student Bot");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        container.add(title, BorderLayout.NORTH);

        // PROMPT AREA
        promptTextArea = new JTextArea(5, 40);
        promptTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        promptTextArea.setLineWrap(true);
        promptTextArea.setWrapStyleWord(true);

        JScrollPane promptScroll = new JScrollPane(promptTextArea);
        promptScroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        container.add(promptScroll, BorderLayout.CENTER);

        // RESPONSE AREA
        responseTextArea = new JTextArea(10, 40);
        responseTextArea.setEditable(false);
        responseTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        responseTextArea.setLineWrap(true);
        responseTextArea.setWrapStyleWord(true);

        JScrollPane responseScroll = new JScrollPane(responseTextArea);
        responseScroll.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        // BOTTOM PANEL
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        sendButton.setBackground(new Color(102, 126, 234));
        sendButton.setForeground(Color.WHITE);

        bottomPanel.add(sendButton, BorderLayout.NORTH);
        bottomPanel.add(responseScroll, BorderLayout.CENTER);

        container.add(bottomPanel, BorderLayout.SOUTH);
        mainPanel.add(container);

        // SEND ACTION
        sendButton.addActionListener(e -> {
            String prompt = promptTextArea.getText();
            responseTextArea.setText("Generating response...");

            new Thread(() -> {
                String res = api.sendPrompt(prompt);
                responseTextArea.setText(res);
            }).start();
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new bot(0).setVisible(true)); 
    }

    // ------------------- API CLASS -------------------
    class GeminiAPI {
        private final String apiKey = "AIzaSyBtKrjFETRjTjRKjnI9yI11dZkrCpzE0JY";

        public String sendPrompt(String prompt) {
            if (prompt.trim().isEmpty())
                return "Please write something in the prompt box.";

            try {
                final String endpoint = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey.trim();
                URL url = new URL(endpoint);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setDoOutput(true);

                String jsonInput = "{ \"contents\": [{ \"parts\": [{ \"text\": \"" +
                        prompt.replace("\"", "\\\"") +
                        "\" }] }] }";

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(jsonInput.getBytes("UTF-8"));
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                br.close();

                return parseResponse(response.toString());

            } catch (Exception e) {
                e.printStackTrace();
                return "Error: " + e.getMessage();
            }
        }

        private String parseResponse(String json) {
            int index = json.indexOf("\"text\":");

            if (index == -1)
                return "No response received.";

            int start = json.indexOf("\"", index + 7) + 1;
            int end = json.indexOf("\"", start);

            if (start == -1 || end == -1)
                return "Parsing error.";

            return json.substring(start, end)
                    .replace("\\n", "\n")
                    .replace("\\\"", "\"");
        }
    }

    // ------------------- ROUNDED PANEL CLASS -------------------
    class RoundedPanel extends JPanel {

        private int cornerRadius = 30;

        public RoundedPanel() {
            super();
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Dimension arcs = new Dimension(cornerRadius, cornerRadius);
            int width = getWidth();
            int height = getHeight();

            Graphics2D graphics = (Graphics2D) g;
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graphics.setColor(Color.WHITE);
            graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);

            graphics.setColor(Color.LIGHT_GRAY);
            graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        }
    }
}

