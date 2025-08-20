package db_lab.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import db_lab.util.UIUtils;
import db_lab.controller.MainController;

public class JuventusMenu {
    private final JFrame frame = new JFrame("Juventus Official Portal");

    public JuventusMenu(Runnable onClose , MainController main) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,620); frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        mainPanel.setBackground(Color.BLACK);

        JLabel title = new JLabel("Juventus Club");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel grid = new JPanel(new GridLayout(3,2,15,15));
        grid.setMaximumSize(new Dimension(600,260)); grid.setBackground(Color.BLACK);

        JButton login = UIUtils.primary("Login Utente");
        JButton reg   = UIUtils.primary("Registrati");
        JButton admin = UIUtils.primary("Area Admin");
        JButton abbo  = UIUtils.primary("Area Abbonati");
        JButton shop  = UIUtils.primary("Shop Online");
        JButton part  = UIUtils.primary("Partite & Classifiche");

        /*login.addActionListener(e -> main.auth());
        reg.addActionListener(e -> main.auth().openRegister());
        admin.addActionListener(e -> main.admin());
        abbo.addActionListener(e -> main.subs());
        shop.addActionListener(e -> main.shop());
        part.addActionListener(e -> main.matches());*/

        grid.add(login); grid.add(reg); grid.add(admin); grid.add(abbo); grid.add(shop); grid.add(part);

        JButton exit = UIUtils.primary("Esci"); exit.addActionListener(e -> System.exit(0));

        mainPanel.add(title); mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(grid);  mainPanel.add(Box.createVerticalStrut(30)); mainPanel.add(exit);

        frame.add(mainPanel);
        frame.addWindowListener(new WindowAdapter(){ public void windowClosing(WindowEvent e){ onClose.run(); }});
        frame.setVisible(true);
    }
}
