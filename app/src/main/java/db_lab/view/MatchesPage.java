package db_lab.view;

import db_lab.controller.Controller;
import db_lab.data.Partita;
import db_lab.util.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Pagina che mostra l'elenco delle partite e, per quella selezionata,
 * il numero di spettatori (righe in presenze) presenti.
 */
public class MatchesPage {
    private final JuventusMenu menu;
    private final JFrame frame;

    private final DefaultListModel<Partita> matchesModel = new DefaultListModel<>();
    private final JList<Partita> matchesList = new JList<>(matchesModel);
    private final JButton btnBack = UIUtils.primary("Indietro");
    private final JLabel lblTitle = new JLabel("Partite & Spettatori");
    private final JLabel lblCount = new JLabel("Seleziona una partita per vedere gli spettatori");
    private final JLabel lblTopSeller = new JLabel("");

    public MatchesPage(JuventusMenu menu, JFrame frame) {
        this.menu = menu;
        this.frame = frame;
        configureUI();
    }

    public void setUp() {
        loadMatches();

        Container cp = frame.getContentPane();
        cp.removeAll();
        cp.setLayout(new BorderLayout());
        cp.setBackground(Color.BLACK);

        // Top bar: back + title centered
        JPanel north = new JPanel(new BorderLayout());
        north.setBackground(Color.BLACK);

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        left.setBackground(Color.BLACK);
        btnBack.setPreferredSize(new Dimension(140, 36));
        left.add(btnBack);
        north.add(left, BorderLayout.WEST);

        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 12));
        center.setBackground(Color.BLACK);
        center.add(lblTitle);
        north.add(center, BorderLayout.CENTER);

        cp.add(north, BorderLayout.NORTH);

        // Center: list of matches + info panel
        matchesList.setCellRenderer(new MatchRenderer());
        matchesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        matchesList.setVisibleRowCount(12);
        matchesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Partita p = matchesList.getSelectedValue();
                updateSpectators(p);
            }
        });

        JScrollPane sp = new JScrollPane(matchesList);
        sp.getViewport().setBackground(Color.BLACK);
        sp.setBorder(BorderFactory.createLineBorder(new Color(255,255,255,40)));

        JPanel right = new JPanel(new BorderLayout());
        right.setBackground(Color.BLACK);
        lblCount.setForeground(Color.WHITE);
        lblCount.setFont(lblCount.getFont().deriveFont(Font.BOLD, 18f));
        right.add(lblCount, BorderLayout.NORTH);

        JPanel info = new JPanel();
        info.setBackground(Color.BLACK);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        lblTopSeller.setForeground(Color.WHITE);
        lblTopSeller.setAlignmentX(Component.LEFT_ALIGNMENT);
        info.add(Box.createVerticalStrut(16));
        info.add(lblTopSeller);
        right.add(info, BorderLayout.CENTER);

        JPanel centerWrap = new JPanel(new GridLayout(1, 2, 16, 0));
        centerWrap.setBackground(Color.BLACK);
        centerWrap.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));
        centerWrap.add(sp);
        centerWrap.add(right);
        cp.add(centerWrap, BorderLayout.CENTER);

        wireActions();

        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    private void loadMatches() {
        matchesModel.clear();
        try {
            Controller c = menu.getController();
            if (c != null) {
                List<Partita> list = c.listPartite();
                for (Partita p : list) {
                    matchesModel.addElement(p);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void updateSpectators(Partita p) {
        if (p == null) {
            lblCount.setText("Seleziona una partita per vedere gli spettatori");
            return;
        }
        try {
            Controller c = menu.getController();
            int n = (c != null) ? c.countSpettatori(p.idPartita) : 0;
            lblCount.setText("Spettatori presenti: " + n);
        } catch (Exception ex) {
            lblCount.setText("Errore nel calcolo spettatori");
        }
    }

    private void wireActions() {
        btnBack.addActionListener(e -> menu.setUp());
    }

    private void configureUI() {
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 22f));
        // Carica subito il top seller magliette
        try {
            Controller c = menu.getController();
            var top = (c != null) ? c.getTopJerseySeller().orElse(null) : null;
            if (top != null) {
                String cognome = (top.cognome == null || top.cognome.isBlank()) ? "" : (" " + top.cognome);
                lblTopSeller.setText("Giocatore con più magliette vendute: " + top.nome + cognome);
            } else {
                lblTopSeller.setText("Giocatore con più magliette vendute: N/D");
            }
        } catch (Exception ex) {
            lblTopSeller.setText("Giocatore con più magliette vendute: errore");
        }
    }

    private static class MatchRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel c = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            c.setOpaque(true);
            c.setBackground(isSelected ? new Color(60,60,60) : Color.BLACK);
            c.setForeground(Color.WHITE);
            if (value instanceof Partita) {
                Partita p = (Partita) value;
                c.setText(p.toDisplayString());
            }
            c.setBorder(BorderFactory.createEmptyBorder(6,10,6,10));
            return c;
        }
    }
}
