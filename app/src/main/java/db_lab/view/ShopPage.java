package db_lab.view;

import db_lab.controller.Controller;
import db_lab.data.Prodotto;
import db_lab.data.Calciatore;
import db_lab.util.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShopPage {
    private final JuventusMenu menu;
    private final JFrame frame;
    private final UserPage userPage;

    private final JButton btnBack = UIUtils.primary("Indietro");
    private final JButton btnCart = UIUtils.primary("Carrello (0)");
    private final JLabel title = new JLabel("Shop");
    private final JLabel lblTotals = new JLabel("");
    private final JButton btnBuy = UIUtils.primary("Acquista");

    private final DefaultListModel<String> modelGenerali = new DefaultListModel<>();
    private final DefaultListModel<String> modelPersonali = new DefaultListModel<>();
    private final DefaultListModel<String> modelBiglietti = new DefaultListModel<>();
    private final DefaultListModel<String> modelVisite = new DefaultListModel<>();

    private final JList<String> listGenerali = new JList<>(modelGenerali);
    private final JList<String> listPersonali = new JList<>(modelPersonali);
    private final JList<String> listBiglietti = new JList<>(modelBiglietti);
    private final JList<String> listVisite = new JList<>(modelVisite);

    // Parallel data lists to map UI selection -> Prodotto
    private final List<Prodotto> generaliItems = new ArrayList<>();
    private final List<Prodotto> personaliItems = new ArrayList<>();
    private final List<Prodotto> bigliettiItems = new ArrayList<>();
    private final List<Prodotto> visiteItems = new ArrayList<>();

    // Cart: unique items keyed by product (and player for personali), preserves insertion order
    private final Map<String, CartItem> cart = new LinkedHashMap<>();

    public ShopPage(JuventusMenu menu, UserPage userPage, JFrame frame) {
        this.menu = menu;
        this.frame = frame;
        this.userPage = userPage;
        configureLookAndFeel();
        wireActions();
    }

    public void setUp() {
        // Load prodotti
        List<Prodotto> prodotti = null;
        try {
            Controller controller = menu.getController();
            if (controller != null) {
                prodotti = controller.listProdotti();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        fillModels(prodotti);

        // Root
        Container cp = frame.getContentPane();
        cp.removeAll();
        cp.setLayout(new BorderLayout());
        cp.setBackground(Color.BLACK);

        // North: back + title
        JPanel north = new JPanel(new BorderLayout());
        north.setBackground(Color.BLACK);
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        left.setBackground(Color.BLACK);
        btnBack.setPreferredSize(new Dimension(140, 36));
        left.add(btnBack);
        north.add(left, BorderLayout.WEST);

        JPanel centerTitle = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 12));
        centerTitle.setBackground(Color.BLACK);
        centerTitle.add(title);
        north.add(centerTitle, BorderLayout.CENTER);
        // Right: cart button
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        right.setBackground(Color.BLACK);
        btnCart.setPreferredSize(new Dimension(160, 36));
        right.add(btnCart);
        north.add(right, BorderLayout.EAST);
        cp.add(north, BorderLayout.NORTH);

        // Center: 2x2 grid with the four sections
        JPanel grid = new JPanel(new GridLayout(2, 2, 16, 16));
        grid.setBackground(Color.BLACK);
        grid.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        grid.add(buildListPanel(new JLabel("Articoli generali"), listGenerali));
        grid.add(buildListPanel(new JLabel("Articoli personali"), listPersonali));
        grid.add(buildListPanel(new JLabel("Biglietti"), listBiglietti));
        grid.add(buildListPanel(new JLabel("Visite guidate"), listVisite));
        cp.add(grid, BorderLayout.CENTER);

        // South: totals + buy button
        JPanel south = new JPanel(new BorderLayout());
        south.setBackground(Color.BLACK);
        south.setBorder(BorderFactory.createEmptyBorder(8, 16, 16, 16));
        lblTotals.setForeground(Color.WHITE);
        lblTotals.setFont(lblTotals.getFont().deriveFont(Font.BOLD, 14f));
        south.add(lblTotals, BorderLayout.WEST);
        JPanel rightBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        rightBtns.setBackground(Color.BLACK);
        rightBtns.add(btnBuy);
        south.add(rightBtns, BorderLayout.EAST);
        cp.add(south, BorderLayout.SOUTH);

        frame.revalidate();
        frame.repaint();
        updateTotals();
        frame.setVisible(true);
    }

    private void fillModels(List<Prodotto> prodotti) {
        modelGenerali.clear(); generaliItems.clear();
        modelPersonali.clear(); personaliItems.clear();
        modelBiglietti.clear(); bigliettiItems.clear();
        modelVisite.clear(); visiteItems.clear();

        if (prodotti == null || prodotti.isEmpty()) {
            return;
        }

        DecimalFormat df = new DecimalFormat("0.00");
        for (Prodotto p : prodotti) {
            String nome = (p.nome == null || p.nome.isBlank()) ? ("Codice " + p.codiceProdotto) : p.nome;
            String price = df.format(p.importo);
            String item = nome + " — € " + price;
            String tip = p.tipologia != null ? p.tipologia : "";
            switch (tip) {
                case "Articologenerale" -> { modelGenerali.addElement(item); generaliItems.add(p);} 
                case "Articolopersonale" -> { modelPersonali.addElement(item); personaliItems.add(p);} 
                case "Biglietto" -> { modelBiglietti.addElement(item); bigliettiItems.add(p);} 
                case "Visitaguidata" -> { modelVisite.addElement(item); visiteItems.add(p);} 
                default -> { modelGenerali.addElement(item); generaliItems.add(p);} // fallback
            }
        }
    }

    private JPanel buildListPanel(JLabel header, JList<String> list) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);
        header.setForeground(Color.WHITE);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 16f));
        header.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        panel.add(header, BorderLayout.NORTH);

        list.setCellRenderer(new DarkListCellRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setFocusable(false);
        list.setVisibleRowCount(10);
        list.setFixedCellHeight(28);
        // Add by double-click or Enter
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JList<?> src = (JList<?>) e.getSource();
                    int idx = src.locationToIndex(e.getPoint());
                    handleAddToCartFor(list, idx);
                }
            }
        });
        list.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    int idx = list.getSelectedIndex();
                    handleAddToCartFor(list, idx);
                }
            }
        });

        JScrollPane sp = new JScrollPane(list);
        sp.getViewport().setBackground(Color.BLACK);
        sp.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 40)));
        panel.add(sp, BorderLayout.CENTER);
        return panel;
    }

    private void configureLookAndFeel() {
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        btnBack.setForeground(Color.BLACK);
        btnCart.setForeground(Color.BLACK);
        btnBuy.setForeground(Color.BLACK);
    }

    private void wireActions() {
        btnBack.addActionListener(e -> userPage.setUp());
        btnCart.addActionListener(e -> showCartDialog());
        btnBuy.addActionListener(e -> onBuy());
    }

    private static class DarkListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel c = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (isSelected) {
                c.setBackground(new Color(60, 60, 60));
                c.setForeground(Color.WHITE);
            } else {
                c.setBackground(Color.BLACK);
                c.setForeground(Color.WHITE);
            }
            c.setOpaque(true);
            c.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
            return c;
        }
    }

    // --- Cart helpers ---
    private void handleAddToCartFor(JList<String> list, int idx) {
        if (idx < 0) return;
        Prodotto p = null;
        if (list == listGenerali) p = generaliItems.get(idx);
        else if (list == listPersonali) p = personaliItems.get(idx);
        else if (list == listBiglietti) p = bigliettiItems.get(idx);
        else if (list == listVisite) p = visiteItems.get(idx);
        if (p == null) return;
        if ("Articolopersonale".equalsIgnoreCase(p.tipologia)) {
            PlayerChoice choice = promptPlayerChoice();
            if (choice == null) return; // cancelled
            addToCart(p, choice.cf, choice.displayName);
        } else {
            addToCart(p, null, null);
        }
    }

    private void addToCart(Prodotto p, String playerCFOrNull, String playerNameOrNull) {
        String key = p.codiceProdotto;
        if (playerCFOrNull != null && !playerCFOrNull.isBlank()) {
            key = key + "#" + playerCFOrNull;
        }
        if (cart.containsKey(key)) {
            JOptionPane.showMessageDialog(frame, "Articolo già nel carrello.");
            return;
        }
        cart.put(key, new CartItem(p, playerCFOrNull, playerNameOrNull));
        updateCartButton();
        String baseName = (p.nome != null && !p.nome.isBlank()) ? p.nome : ("Codice " + p.codiceProdotto);
        String extra = (playerNameOrNull != null && !playerNameOrNull.isBlank()) ? (" — " + playerNameOrNull) : "";
        JOptionPane.showMessageDialog(frame, "Aggiunto al carrello: " + baseName + extra);
        updateTotals();
    }

    private void updateCartButton() {
        btnCart.setText("Carrello (" + cart.size() + ")");
    }

    private void showCartDialog() {
        JDialog dialog = new JDialog(frame, "Carrello", true);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(Color.BLACK);

        DefaultListModel<String> cartModel = new DefaultListModel<>();
        java.util.List<String> keys = new java.util.ArrayList<>(cart.keySet());
        DecimalFormat df = new DecimalFormat("0.00");
        for (String key : keys) {
            CartItem item = cart.get(key);
            Prodotto p = item.prodotto;
            String nome = (p.nome == null || p.nome.isBlank()) ? ("Codice " + p.codiceProdotto) : p.nome;
            String who = (item.playerName != null && !item.playerName.isBlank()) ? (" — " + item.playerName) : "";
            cartModel.addElement(nome + who + " — € " + df.format(p.importo));
        }
        JList<String> cartList = new JList<>(cartModel);
        cartList.setCellRenderer(new CartCellRenderer());
        cartList.setVisibleRowCount(12);
        cartList.setFixedCellHeight(32);

        JScrollPane sp = new JScrollPane(cartList);
        sp.getViewport().setBackground(Color.BLACK);
        sp.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 40)));

        JPanel north = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 12));
        north.setBackground(Color.BLACK);
        JLabel lbl = new JLabel("Articoli nel carrello: " + cart.size());
        lbl.setForeground(Color.WHITE);
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD, 16f));
        north.add(lbl);

        JButton close = UIUtils.primary("Chiudi");
        close.addActionListener(e -> dialog.dispose());
        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        south.setBackground(Color.BLACK);
        south.add(close);

        dialog.add(north, BorderLayout.NORTH);
        dialog.add(sp, BorderLayout.CENTER);
        dialog.add(south, BorderLayout.SOUTH);
        dialog.setSize(420, 380);
        dialog.setLocationRelativeTo(frame);
        // Interaction: click on right area or double-click removes the item
        final int REMOVE_HIT_WIDTH = 100;
        cartList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idx = cartList.locationToIndex(e.getPoint());
                if (idx < 0) return;
                Rectangle bounds = cartList.getCellBounds(idx, idx);
                int relX = e.getX() - bounds.x;
                boolean onRemove = relX > bounds.width - REMOVE_HIT_WIDTH;
                if (onRemove || e.getClickCount() == 2) {
                    removeFromCartAt(cartModel, keys, lbl, idx);
                }
            }
        });
        dialog.setVisible(true);
    }

    private void removeFromCartAt(DefaultListModel<String> cartModel, java.util.List<String> keys, JLabel header, int idx) {
        if (idx < 0 || idx >= keys.size()) return;
        String key = keys.get(idx);
        cart.remove(key);
        keys.remove(idx);
        cartModel.remove(idx);
        header.setText("Articoli nel carrello: " + cart.size());
        updateCartButton();
        updateTotals();
    }

    private record PlayerChoice(String cf, String displayName) {}

    private PlayerChoice promptPlayerChoice() {
        Controller controller = menu.getController();
        java.util.List<Calciatore> players = null;
        try {
            if (controller != null) players = controller.listCalciatori();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (players == null || players.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nessun calciatore disponibile.");
            return null;
        }
        String[] display = new String[players.size()];
        for (int i = 0; i < players.size(); i++) {
            Calciatore c = players.get(i);
            String name = (c.nome != null ? c.nome : "") + (c.cognome != null ? (" " + c.cognome) : "");
            String num = "";
            try { if (c.numeroMaglia > 0) num = " (#" + c.numeroMaglia + ")"; } catch (Exception ignore) {}
            display[i] = name.trim() + num;
        }
        String selection = (String) JOptionPane.showInputDialog(
                frame,
                "Seleziona il calciatore:",
                "Articolo personale",
                JOptionPane.QUESTION_MESSAGE,
                null,
                display,
                display[0]
        );
        if (selection == null) return null; // cancelled
        int index = java.util.Arrays.asList(display).indexOf(selection);
        if (index < 0) index = 0;
        Calciatore chosen = players.get(index);
        String name = (chosen.nome != null ? chosen.nome : "") + (chosen.cognome != null ? (" " + chosen.cognome) : "");
        String num = "";
        try { if (chosen.numeroMaglia > 0) num = " (#" + chosen.numeroMaglia + ")"; } catch (Exception ignore) {}
        return new PlayerChoice(chosen.CF, (name.trim() + num));
    }

    // Renderer for cart entries with a right-aligned "Rimuovi" badge
    private static class CartCellRenderer extends JPanel implements ListCellRenderer<String> {
        private final JLabel text = new JLabel();
        private final JLabel remove = new JLabel("Rimuovi");
        public CartCellRenderer() {
            super(new BorderLayout());
            setOpaque(true);
            text.setOpaque(false);
            text.setForeground(Color.WHITE);
            remove.setOpaque(true);
            remove.setBackground(new Color(80,80,80));
            remove.setForeground(Color.WHITE);
            remove.setBorder(BorderFactory.createEmptyBorder(4,8,4,8));
            JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            right.setOpaque(false);
            right.add(remove);
            add(text, BorderLayout.WEST);
            add(right, BorderLayout.EAST);
            setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        }
        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
            text.setText(String.valueOf(value));
            if (isSelected) {
                setBackground(new Color(60,60,60));
            } else {
                setBackground(Color.BLACK);
            }
            return this;
        }
    }

    private void updateTotals() {
        double total = 0.0;
        for (CartItem item : cart.values()) {
            total += item.prodotto.importo;
        }
        double scontoPerc = 0.0;
        var cliente = userPage.getCliente();
        try {
            if (cliente != null && cliente.CF != null && !cliente.CF.isBlank()) {
                Controller controller = menu.getController();
                if (controller != null) {
                    scontoPerc = controller.getScontoPercentuale(cliente.CF, 2026);
                }
            }
        } catch (Exception ignore) {}
        DecimalFormat df = new DecimalFormat("0.00");
        if (scontoPerc > 0.0) {
            double discounted = total * (1.0 - (scontoPerc / 100.0));
            lblTotals.setText("Totale: € " + df.format(total) + " — Scontato: € " + df.format(discounted) + " (" + df.format(scontoPerc) + "%)");
        } else {
            lblTotals.setText("Totale: € " + df.format(total));
        }
    }

    private void onBuy() {
        // For now, only show a summary of total and discounted total; does not persist order/items yet.
        double total = 0.0;
        for (CartItem item : cart.values()) total += item.prodotto.importo;
        double scontoPerc = 0.0;
        var cliente = userPage.getCliente();
        try {
            if (cliente != null && cliente.CF != null && !cliente.CF.isBlank()) {
                Controller controller = menu.getController();
                if (controller != null) scontoPerc = controller.getScontoPercentuale(cliente.CF, 2026);
            }
        } catch (Exception ignore) {}
        DecimalFormat df = new DecimalFormat("0.00");
        String message;
        if (scontoPerc > 0.0) {
            double discounted = total * (1.0 - (scontoPerc / 100.0));
            message = "Totale carrello: € " + df.format(total) + "\n" +
                      "Sconto abbonato (" + df.format(scontoPerc) + "%): -€ " + df.format(total - discounted) + "\n" +
                      "Da pagare: € " + df.format(discounted);
        } else {
            message = "Totale carrello: € " + df.format(total) + "\n" +
                      "Nessuno sconto disponibile.";
        }
        JOptionPane.showMessageDialog(frame, message, "Riepilogo acquisto", JOptionPane.INFORMATION_MESSAGE);
    }

    private static class CartItem {
        final Prodotto prodotto;
        final String playerCF;
        final String playerName;
        CartItem(Prodotto p, String cf, String name) {
            this.prodotto = p;
            this.playerCF = cf;
            this.playerName = name;
        }
    }
}
