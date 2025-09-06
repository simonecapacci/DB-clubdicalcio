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

    // Bottoni
    private final JButton btnShop = UIUtils.primary("Shop");
    private final JButton btnAbbonati = UIUtils.primary("Abbonati");
    private final JButton btnTopPlayers = UIUtils.primary("Migliori giocatori");
    private final JButton btnBack = UIUtils.primary("Indietro");

    public UserPage(JuventusMenu menu, JFrame frame, Cliente maybeCliente) {
        this.menu = menu;
        this.frame = frame;
        this.cliente = maybeCliente;
    }
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
        for (JButton b : new JButton[]{btnShop, btnAbbonati, btnTopPlayers}) {
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
        Controller controller = (menu != null) ? menu.getController() : null;

        btnShop.addActionListener(e -> {
            // TODO: apri/passa alla pagina "Shop"
            // Esempio di punto di aggancio:
            // if (controller != null) controller.userRequestedShopPage(frame);
            JOptionPane.showMessageDialog(frame, "Aprire pagina Shop (TODO)");
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
}
