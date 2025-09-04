package db_lab.view;

import db_lab.data.Dirigente;
import db_lab.util.UIUtils;

import javax.swing.*;
import java.awt.*;

public class AdminPage {
    private final JuventusMenu menu;
    private final JFrame frame;
    private final Dirigente admin;

    private final JButton btnManageStaff = UIUtils.primary("Gestisci dipendenti");
    private final JButton btnTopClients  = UIUtils.primary("Migliori clienti");
    private final JButton btnBestMatch   = UIUtils.primary("Partita più redditizia");
    private final JButton btnBack        = UIUtils.primary("Indietro");

    public AdminPage(JuventusMenu menu, JFrame frame, Dirigente admin) {
        this.menu = menu;
        this.frame = frame;
        this.admin = admin;
    }

    public void setUp() {
        Container cp = frame.getContentPane();
        cp.removeAll();
        cp.setLayout(new BorderLayout());

        // Top bar: Back on the left, optional greeting on the right
        JPanel north = new JPanel(new BorderLayout());
        north.setBackground(Color.BLACK);

        JPanel backWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        backWrap.setBackground(Color.BLACK);
        btnBack.setMaximumSize(new Dimension(140, 36));
        backWrap.add(btnBack);
        north.add(backWrap, BorderLayout.WEST);

        if (admin != null) {
            String who = (admin.nome != null ? admin.nome : "Admin");
            JLabel hello = new JLabel("Ciao, " + who + "!");
            hello.setFont(new Font("Arial", Font.BOLD, 16));
            hello.setForeground(Color.WHITE);
            JPanel greetWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
            greetWrap.setBackground(Color.BLACK);
            greetWrap.add(hello);
            north.add(greetWrap, BorderLayout.EAST);
        }
        cp.add(north, BorderLayout.NORTH);

        // Center content: title + vertical buttons
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        center.setBackground(Color.BLACK);

        JLabel title = new JLabel("Area Admin");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(title);
        center.add(Box.createRigidArea(new Dimension(0, 18)));

        for (JButton b : new JButton[]{btnManageStaff, btnTopClients, btnBestMatch}) {
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            center.add(b);
            center.add(Box.createRigidArea(new Dimension(0, 12)));
        }

        cp.add(center, BorderLayout.CENTER);

        wireActions();

        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    private void wireActions() {
        btnBack.addActionListener(e -> menu.setUp());

        btnManageStaff.addActionListener(e -> {
            var cp = frame.getContentPane();
            cp.removeAll();
            ManageStaffPage page = new ManageStaffPage(menu, this, frame);
            page.setUp();
        });

        btnTopClients.addActionListener(e ->
            JOptionPane.showMessageDialog(frame, "Mostrare migliori clienti (TODO)")
        );

        btnBestMatch.addActionListener(e ->
            JOptionPane.showMessageDialog(frame, "Mostrare partita più redditizia (TODO)")
        );
    }
}
