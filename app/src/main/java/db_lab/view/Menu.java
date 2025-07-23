package juventus.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JuventusMenu {

    private JFrame mainFrame;

    public JuventusMenu(Runnable onClose) {
        this.mainFrame = setUp(onClose);
    }

    private JFrame setUp(Runnable onClose) {
        JFrame frame = new JFrame("Juventus Official Portal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.BLACK);

        JLabel title = new JLabel("Juventus Club");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(30));

        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        buttonPanel.setMaximumSize(new Dimension(500, 200));
        buttonPanel.setBackground(Color.BLACK);

        JButton loginButton = createStyledButton("Login Utente");
        JButton registerButton = createStyledButton("Registrati");
        JButton adminButton = createStyledButton("Area Admin");
        JButton abbonatiButton = createStyledButton("Area Abbonati");
        JButton shopButton = createStyledButton("Shop Online");
        JButton partiteButton = createStyledButton("Partite & Classifiche");

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(adminButton);
        buttonPanel.add(abbonatiButton);
        buttonPanel.add(shopButton);
        buttonPanel.add(partiteButton);

        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(30));

        JButton exitButton = createStyledButton("Esci");
        exitButton.addActionListener(e -> System.exit(0));
        mainPanel.add(exitButton);

        frame.add(mainPanel);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose.run();
                System.exit(0);
            }
        });

        frame.setVisible(true);
        return frame;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setMaximumSize(new Dimension(200, 40));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JuventusMenu(() -> System.out.println("Chiusura app Juventus.")));
    }
}