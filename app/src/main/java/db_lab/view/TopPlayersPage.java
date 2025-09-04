package db_lab.view;

import db_lab.controller.Controller;
import db_lab.data.AssistPersonali;
import db_lab.data.GoalPersonali;
import db_lab.util.UIUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Mostra due liste affiancate: "Migliori marcatori" e "Migliori assistman".
 * Riempie lo stesso JFrame usato da JuventusMenu, mantenendo sfondo nero e testi bianchi.
 */
public class TopPlayersPage {
    private final JuventusMenu menu; // facoltativo: per tornare indietro
    private final JFrame frame;
    private final UserPage userPage;

    // Modelli dati
    private final DefaultListModel<String> scorersModel = new DefaultListModel<>();
    private final DefaultListModel<String> assistsModel = new DefaultListModel<>();

    // Componenti UI
    private final JButton btnBack = UIUtils.primary("Indietro");
    private final JLabel title = new JLabel("Migliori giocatori");
    private final JLabel lblScorers = new JLabel("Migliori marcatori");
    private final JLabel lblAssists = new JLabel("Migliori assistman");
    private final JList<String> listScorers = new JList<>(scorersModel);
    private final JList<String> listAssists = new JList<>(assistsModel);

    public TopPlayersPage(JuventusMenu menu, UserPage userPage, JFrame frame) {
        this.menu = menu;
        this.frame = frame;
        this.userPage = userPage;
        configureLookAndFeel();
        wireActions();
    }

    /**
     * Costruisce e mostra la pagina dentro al frame passato.
     * Se le liste sono null o vuote, mostra dei placeholder.
     */
    public void setUp() {
        // Recupera le liste dal Controller del menu
        List<GoalPersonali> marcatori = null;
        List<AssistPersonali> assistman = null;
        try {
            Controller controller = menu.getController();
            if (controller != null) {
                marcatori = controller.getTopScorers();
                assistman = controller.getTopAssistmen();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        fillModelGoals(scorersModel, marcatori);
        fillModelAssists(assistsModel, assistman);

        // Root
        Container cp = frame.getContentPane();
        cp.removeAll();
        cp.setLayout(new BorderLayout());
        cp.setBackground(Color.BLACK);

        // Barra in alto: back + titolo
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
        cp.add(north, BorderLayout.NORTH);

        // Sezione centrale con due colonne
        JPanel columns = new JPanel(new GridLayout(1, 2, 16, 0));
        columns.setBackground(Color.BLACK);
        columns.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        columns.add(buildListPanel(lblScorers, listScorers));
        columns.add(buildListPanel(lblAssists, listAssists));

        cp.add(columns, BorderLayout.CENTER);

        // Render
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    private void fillModelGoals(DefaultListModel<String> model, List<GoalPersonali> data) {
        model.clear();
        if (data != null && !data.isEmpty()) {
            for (GoalPersonali gp : data) {
                var nome = gp.nome;
                var cognome = gp.cognome;
                var goal = gp.goal;
                model.addElement(nome + (cognome == null || cognome.isBlank() ? "" : (" " + cognome))
                + " — " + goal + " goal");    
            }
        }   
    }

    private void fillModelAssists(DefaultListModel<String> model, List<AssistPersonali> data) {
        model.clear();
        if (data != null && !data.isEmpty()) {
            for (AssistPersonali gp : data) {
                var nome = gp.nome;
                var cognome = gp.cognome;
                var assists = gp.assists;
                model.addElement(nome + (cognome == null || cognome.isBlank() ? "" : (" " + cognome))
                + " — " + assists + " assist");    
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
        list.setVisibleRowCount(12);
        list.setFixedCellHeight(28);

        JScrollPane sp = new JScrollPane(list);
        sp.getViewport().setBackground(Color.BLACK);
        sp.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 40)));
        sp.setPreferredSize(new Dimension(360, 300));
        panel.add(sp, BorderLayout.CENTER);
        return panel;
    }

    private void configureLookAndFeel() {
        // Titolo pagina
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));

        // Bottoni UIUtils già stilizzati; per coerenza su sfondo nero:
        btnBack.setForeground(Color.BLACK);
    }

    private void wireActions() {
        btnBack.addActionListener(e -> {
            userPage.setUp();
        });
    }

    /** Renderer per JList in tema scuro (sfondo nero, testo bianco). */
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
}

