package db_lab.view;

import db_lab.data.Dirigente;
import db_lab.util.UIUtils;
import db_lab.controller.Controller;
import db_lab.data.BestClient;

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

        btnTopClients.addActionListener(e -> showBestClientsDialog());

        btnBestMatch.addActionListener(e -> showBestMatchDialog());
    }

    private void showBestClientsDialog() {
        Controller controller = menu.getController();
        java.util.List<BestClient> best = java.util.Collections.emptyList();
        try {
            if (controller != null) best = controller.listBestClients();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (best == null || best.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nessun ordine trovato.");
            return;
        }

        JDialog dialog = new JDialog(frame, "Migliori clienti", true);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(Color.BLACK);

        String[] cols = {"Pos", "CF", "Nome", "Cognome", "Totale Speso (€)"};
        Object[][] data = new Object[best.size()][cols.length];
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        for (int i = 0; i < best.size(); i++) {
            BestClient bc = best.get(i);
            data[i][0] = (i + 1);
            data[i][1] = bc.CF;
            data[i][2] = bc.nome;
            data[i][3] = bc.cognome;
            data[i][4] = df.format(bc.totaleSpeso);
        }
        JTable table = new JTable(data, cols);
        table.setFillsViewportHeight(true);
        table.setEnabled(false);
        JScrollPane sp = new JScrollPane(table);
        dialog.add(sp, BorderLayout.CENTER);

        JButton close = UIUtils.primary("Chiudi");
        close.addActionListener(e -> dialog.dispose());
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        south.setBackground(Color.BLACK);
        south.add(close);
        dialog.add(south, BorderLayout.SOUTH);

        dialog.setSize(700, 420);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void showBestMatchDialog() {
        Controller controller = menu.getController();
        java.util.Optional<db_lab.data.MatchRevenue> opt = java.util.Optional.empty();
        try {
            if (controller != null) opt = controller.getMostProfitableMatch();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (opt == null || opt.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nessun incasso disponibile.");
            return;
        }
        var mr = opt.get();

        JDialog dialog = new JDialog(frame, "Partita più redditizia", true);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(Color.BLACK);

        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");

        JPanel center = new JPanel();
        center.setLayout(new GridLayout(0, 2, 10, 10));
        center.setBackground(Color.BLACK);
        center.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        addRow(center, "Data:", mr.data);
        addRow(center, "Avversaria:", mr.squadraAvversaria);
        addRow(center, "Competizione:", mr.competizione);
        addRow(center, "Risultato:", mr.risultato);
        addRow(center, "Biglietti venduti:", String.valueOf(mr.bigliettiVenduti));
        addRow(center, "Incasso totale (€):", df.format(mr.totaleIncasso));

        JButton close = UIUtils.primary("Chiudi");
        close.addActionListener(e -> dialog.dispose());
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        south.setBackground(Color.BLACK);
        south.add(close);

        dialog.add(center, BorderLayout.CENTER);
        dialog.add(south, BorderLayout.SOUTH);
        dialog.setSize(520, 320);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private static void addRow(JPanel panel, String label, String value) {
        JLabel l = new JLabel(label);
        l.setForeground(Color.WHITE);
        l.setFont(l.getFont().deriveFont(Font.BOLD));
        JLabel v = new JLabel(value != null ? value : "");
        v.setForeground(Color.WHITE);
        panel.add(l);
        panel.add(v);
    }
}
