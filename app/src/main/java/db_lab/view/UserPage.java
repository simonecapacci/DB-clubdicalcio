package db_lab.view;

import db_lab.controller.Controller;
import db_lab.data.Cliente;
import db_lab.util.UIUtils;
import db_lab.view.JuventusMenu;

import javax.swing.*;
import java.awt.*;

public class UserPage {
    private final JuventusMenu menu;
    private final JFrame frame;
    private final Cliente cliente;
    private boolean actionsWired = false;

    // Bottoni
    private final JButton btnShop = UIUtils.primary("Shop");
    private final JButton btnAbbonati = UIUtils.primary("Abbonati");
    private final JButton btnTopPlayers = UIUtils.primary("Migliori giocatori");
    private final JButton btnBack = UIUtils.primary("Indietro");
    private final JButton btnRefund = UIUtils.primary("Chiedi reso");

    public UserPage(JuventusMenu menu, JFrame frame, Cliente maybeCliente) {
        this.menu = menu;
        this.frame = frame;
        this.cliente = maybeCliente;
    }
    public Cliente getCliente() { return this.cliente; }
    //qjsL8Xzc
    //martina.bianchi629@email.com
    /** Costruisce e mostra la pagina utente dentro lo stesso frame del menu. */
    public void setUp() {
        Container cp = frame.getContentPane();
        cp.removeAll();
        cp.setLayout(new BorderLayout());

        // --- Barra superiore con "Indietro" in disparte ---
        JPanel north = new JPanel(new BorderLayout());
        north.setBackground(Color.BLACK);
        JPanel backWrap = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        // il bottone "Indietro" un filo più compatto
        btnBack.setMaximumSize(new Dimension(140, 36));
        backWrap.add(btnBack);
        backWrap.setBackground(Color.BLACK);
        north.add(backWrap, BorderLayout.WEST);

        // opzionale: saluto a destra se disponibile il cliente
        if (cliente != null) {
            String nome = (cliente.Nome != null) ? cliente.Nome : "Utente";
            JLabel hello = new JLabel("Ciao, " + nome + "!");
            hello.setFont(new Font("Arial", Font.BOLD, 16));
            hello.setForeground(Color.WHITE);
            JPanel greetWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
            greetWrap.add(hello);
            greetWrap.setBackground(Color.BLACK);
            north.add(greetWrap, BorderLayout.EAST);
        }
        cp.add(north, BorderLayout.NORTH);

        // --- Contenuto centrale con i pulsanti principali allineati verticalmente ---
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        center.setBackground(Color.BLACK);

        // Titolo pagina (stile semplice, coerente)
        JLabel title = new JLabel("Area Utente");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(title);
        center.add(Box.createRigidArea(new Dimension(0, 18)));

        // Pulsanti centrali
        for (JButton b : new JButton[]{btnShop, btnAbbonati, btnTopPlayers, btnRefund}) {
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            center.add(b);
            center.add(Box.createRigidArea(new Dimension(0, 12)));
        }

        cp.add(center, BorderLayout.CENTER);

        // --- Azioni ---
        wireActions();

        // --- Render finale ---
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    private void wireActions() {
        if (actionsWired) return; // avoid duplicate listeners on repeated setUp()
        actionsWired = true;
        Controller controller = (menu != null) ? menu.getController() : null;

        btnShop.addActionListener(e -> {
            this.goShopPage();
        });

        btnAbbonati.addActionListener(e -> {
            if (cliente == null || cliente.CF == null || cliente.CF.isBlank()) {
                JOptionPane.showMessageDialog(frame, "Effettua il login per abbonarti.", "Non autenticato", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (controller != null && controller.hasAbbonamento(cliente.CF, 2026)) {
                JOptionPane.showMessageDialog(frame, "Sei già abbonato per la stagione 2026.", "Già abbonato", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Pop-up per scegliere il tipo di abbonamento
            String[] tipi = {"completo", "normale", "essenziale"};
            String tipo = (String) JOptionPane.showInputDialog(
                    frame,
                    "Scegli il tipo di abbonamento:",
                    "Abbonamento",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    tipi,
                    tipi[0]
            );

            if (tipo == null) {
                // Annullato dall'utente
                return;
            }

            boolean ok = false;
            if (controller != null) {
                ok = controller.addAbbonamento(cliente.CF, tipo, 2026);
            }

            if (ok) {
                JOptionPane.showMessageDialog(frame, "Benvenuto tra gli abbonati!");
            } else {
                // Potrebbe essere fallito per race-condition/duplicato
                JOptionPane.showMessageDialog(frame, "Impossibile completare l'abbonamento (forse sei già abbonato).", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnTopPlayers.addActionListener(e -> {
            // TODO: apri/passa alla pagina "Migliori giocatori"
            this.goTopPlayersPage();
        });

        btnRefund.addActionListener(e -> onRefund());

        btnBack.addActionListener(e -> {
            // Torna al menu principale dentro LO STESSO frame.
            // Se nel tuo JuventusMenu esiste un metodo pubblico che ricostruisce la home (es. setUp(), showHome(), ecc.),
            // chiamalo qui. Per compatibilità lasciamo un fallback neutro.
            try {
                JuventusMenu.class.getMethod("setUp").invoke(menu);
            } catch (Exception noSetUp) {
                // Fallback: svuota e mostra un placeholder (evita crash se il metodo non esiste).
                Container cp = frame.getContentPane();
                cp.removeAll();
                JPanel placeholder = new JPanel(new GridBagLayout());
                placeholder.add(new JLabel("Torna al menu principale (implementa menu.setUp() per ripristinare la home)"));
                cp.add(placeholder, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
            }
        });
    }
    private void goTopPlayersPage() {
        var cp = frame.getContentPane();
        cp.removeAll();
        final TopPlayersPage tpp = new TopPlayersPage(menu, this, frame);
        tpp.setUp();
    }

    private void goShopPage() {
        var cp = frame.getContentPane();
        cp.removeAll();
        final ShopPage shop = new ShopPage(menu, this, frame);
        shop.setUp();
    }

    private void onRefund() {
        if (cliente == null || cliente.CF == null || cliente.CF.isBlank()) {
            JOptionPane.showMessageDialog(frame, "Devi essere loggato per richieste di reso.");
            return;
        }
        Controller controller = menu.getController();
        java.util.List<db_lab.data.OrderInfo> tmpOrders = null;
        try {
            if (controller != null) tmpOrders = controller.listRecentOrders(cliente.CF);
        } catch (Exception ex) { ex.printStackTrace(); }
        final java.util.List<db_lab.data.OrderInfo> orders = (tmpOrders != null) ? tmpOrders : java.util.Collections.emptyList();
        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nessun ordine negli ultimi 30 giorni.");
            return;
        }

        // Build dialog with list of orders
        JDialog dialog = new JDialog(frame, "I tuoi ordini (ultimi 30 giorni)", true);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(Color.BLACK);

        DefaultListModel<String> model = new DefaultListModel<>();
        for (db_lab.data.OrderInfo oi : orders) {
            model.addElement("Ordine #" + oi.codiceOrdine + " — " + oi.data);
        }
        JList<String> list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setVisibleRowCount(10);
        list.setFixedCellHeight(28);
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override public java.awt.Component getListCellRendererComponent(JList<?> l, Object v, int i, boolean s, boolean f) {
                var c = (JLabel) super.getListCellRendererComponent(l, v, i, s, f);
                c.setBackground(s ? new Color(60,60,60) : Color.BLACK);
                c.setForeground(Color.WHITE);
                c.setOpaque(true);
                c.setBorder(BorderFactory.createEmptyBorder(6,10,6,10));
                return c;
            }
        });
        JScrollPane sp = new JScrollPane(list);
        sp.getViewport().setBackground(Color.BLACK);
        sp.setBorder(BorderFactory.createLineBorder(new Color(255,255,255,40)));
        dialog.add(sp, BorderLayout.CENTER);

        JButton close = UIUtils.primary("Chiudi");
        JButton details = UIUtils.primary("Dettagli");
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        south.setBackground(Color.BLACK);
        south.add(close); south.add(details);
        dialog.add(south, BorderLayout.SOUTH);

        Runnable openDetails = () -> {
            int idx = list.getSelectedIndex();
            if (idx < 0) return;
            db_lab.data.OrderInfo oi = orders.get(idx);
            showOrderDetailsDialog(oi.codiceOrdine);
        };
        details.addActionListener(e -> openDetails.run());
        list.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) openDetails.run();
            }
        });
        close.addActionListener(e -> dialog.dispose());

        dialog.setSize(480, 360);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }

    private void showOrderDetailsDialog(int codiceOrdine) {
        Controller controller = menu.getController();
        java.util.List<db_lab.data.OrderItem> items = java.util.Collections.emptyList();
        db_lab.util.Pair<Double, Double> totals = db_lab.util.Pair.of(0.0, 0.0);
        try {
            if (controller != null) {
                items = controller.listOrderItems(codiceOrdine);
                totals = controller.getOrderTotals(codiceOrdine);
            }
        } catch (Exception ex) { ex.printStackTrace(); }

        JDialog dialog = new JDialog(frame, "Dettagli ordine #" + codiceOrdine, true);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(Color.BLACK);

        String[] cols = {"Articolo", "Tipologia", "Prezzo (€)"};
        Object[][] data = new Object[items.size()][3];
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.00");
        for (int i = 0; i < items.size(); i++) {
            var it = items.get(i);
            data[i][0] = it.nome;
            data[i][1] = it.tipologia;
            data[i][2] = df.format(it.importo);
        }
        JTable table = new JTable(data, cols); table.setEnabled(false);
        JScrollPane sp = new JScrollPane(table);
        dialog.add(sp, BorderLayout.CENTER);

        double lordo = totals.first;
        double netto = totals.second;
        JLabel lbl = new JLabel("Totale: € " + df.format(lordo) + (netto != lordo ? (" — Scontato: € " + df.format(netto)) : ""));
        lbl.setForeground(Color.WHITE);

        JButton refund = UIUtils.primary("Chiedi reso");
        JButton close = UIUtils.primary("Chiudi");
        JPanel south = new JPanel(new BorderLayout());
        south.setBackground(Color.BLACK);
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); left.setBackground(Color.BLACK); left.add(lbl);
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10)); right.setBackground(Color.BLACK); right.add(close); right.add(refund);
        south.add(left, BorderLayout.WEST); south.add(right, BorderLayout.EAST);
        dialog.add(south, BorderLayout.SOUTH);

        close.addActionListener(e -> dialog.dispose());
        refund.addActionListener(e -> {
            int ans = JOptionPane.showConfirmDialog(dialog, "Confermi la richiesta di reso per l'ordine #" + codiceOrdine + "?", "Conferma reso", JOptionPane.YES_NO_OPTION);
            if (ans != JOptionPane.YES_OPTION) return;
            try {
                if (controller != null && controller.orderHasPastVisit(codiceOrdine)) {
                    JOptionPane.showMessageDialog(dialog, "La visita è gia stata effettuata, quindi il reso non è più disponibile");
                    return;
                }
            } catch (Exception ex) { ex.printStackTrace(); }
            boolean ok = false;
            try { if (controller != null) ok = controller.refundOrder(cliente.CF, codiceOrdine); } catch (Exception ex) { ex.printStackTrace(); }
            if (ok) {
                JOptionPane.showMessageDialog(dialog, "Ordine rimborsato ed eliminato.");
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Impossibile completare il reso.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setSize(640, 420);
        dialog.setLocationRelativeTo(frame);
        dialog.setVisible(true);
    }
}
