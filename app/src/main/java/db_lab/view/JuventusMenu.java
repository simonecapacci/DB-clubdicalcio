package db_lab.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.Optional;

import db_lab.util.UIUtils;
import db_lab.controller.Controller;

public class JuventusMenu {
    private final JFrame frame;
    private Controller controller;
    private Login login;
    private final Runnable onClose;
    public JuventusMenu(Runnable onClose){
        frame = new JFrame("Juventus Official Portal");
        this.onClose = onClose;
    }
    public void setUp() {
        frame.getContentPane().removeAll();
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

        JButton loginbtn = UIUtils.primary("Login Utente");
        JButton reg   = UIUtils.primary("Registrati");
        JButton admin = UIUtils.primary("Area Admin");
        JButton abbo  = UIUtils.primary("Area Abbonati");
        JButton shop  = UIUtils.primary("Shop Online");
        JButton part  = UIUtils.primary("Partite & Classifiche");

        loginbtn.addActionListener(e -> auth());
        //reg.addActionListener(e -> auth().openRegister());
        //admin.addActionListener(e -> admin());
        //abbo.addActionListener(e -> subs());
        //shop.addActionListener(e -> shop());
        //part.addActionListener(e -> matches());

        grid.add(loginbtn); grid.add(reg); grid.add(admin); grid.add(abbo); grid.add(shop); grid.add(part);

        JButton exit = UIUtils.primary("Esci"); exit.addActionListener(e -> System.exit(0));

        mainPanel.add(title); mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(grid);  mainPanel.add(Box.createVerticalStrut(30)); mainPanel.add(exit);

        frame.add(mainPanel);
        frame.addWindowListener(new WindowAdapter(){ public void windowClosing(WindowEvent e){ onClose.run(); }});
        frame.setVisible(true);
    }

    public void setController(Controller controller) {
        Objects.requireNonNull(controller, "Set null controller in view");
        this.controller = controller;
    }

    public void auth(){
        login = new Login(this, frame);
    }
    
    public Controller getController(){
        return this.controller;
    }
}
