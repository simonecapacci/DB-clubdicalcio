package db_lab.view;

import db_lab.controller.Controller;
import db_lab.util.UIUtils;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Dialog di registrazione utente (Nome, Cognome, CF, Email, Password).
 * - Se CF già presente: messaggio e apertura Login.
 * - Se nuovo: registra su DB, messaggio OK e apertura Login.
 */
public class UserRegister {

    private final JuventusMenu menu;
    private final JFrame owner;
    private JDialog dialog;

    private JTextField nomeField;
    private JTextField cognomeField;
    private JTextField cfField;
    private JTextField indirizzoField;
    private JTextField emailField;
    private JPasswordField passField;

    public UserRegister(JuventusMenu menu, JFrame owner) {
        this.menu   = menu;
        this.owner  = owner;
    }

    public void open() {
        dialog = new JDialog(owner, "Registrazione Utente", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setSize(560, 520);
        dialog.setLocationRelativeTo(owner);

        JPanel root = new JPanel();
        root.setBackground(Color.BLACK);
        root.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Crea il tuo account");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.BLACK);
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8,8,8,8);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridy = 0;

        // Campi
        nomeField      = textField(20);
        cognomeField   = textField(20);
        cfField        = textField(16);
        indirizzoField = textField(30);
        emailField     = textField(24);
        passField      = new JPasswordField(24);

        // CF: limita a 16 e upper-case
        ((AbstractDocument) cfField.getDocument()).setDocumentFilter(new LengthFilter(16));

        // Labels
        addRow(form, gc, "Nome",    nomeField);
        addRow(form, gc, "Cognome", cognomeField);
        addRow(form, gc, "CF",      cfField);
        addRow(form, gc, "Indirizzo", indirizzoField);
        addRow(form, gc, "Email",   emailField);
        addRow(form, gc, "Password",passField);

        // Bottoni
        JPanel buttons = new JPanel(new GridLayout(1,2,12,12));
        buttons.setBackground(Color.BLACK);

        JButton createBtn = UIUtils.primary("Crea account");
        JButton loginBtn  = UIUtils.primary("Ho già un account");

        createBtn.addActionListener(this::handleCreate);
        loginBtn.addActionListener(e -> {
            dialog.dispose();
            new UserLogin(menu, owner); // apre la login
        });

        buttons.add(createBtn);
        buttons.add(loginBtn);

        root.add(title);
        root.add(Box.createVerticalStrut(20));
        root.add(form);
        root.add(Box.createVerticalStrut(20));
        root.add(buttons);

        dialog.setContentPane(root);
        dialog.setVisible(true);
    }

    private void handleCreate(ActionEvent e) {
        String nome   = nomeField.getText().trim();
        String cogn   = cognomeField.getText().trim();
        String cf     = cfField.getText().trim().toUpperCase();
        String indirizzo = indirizzoField.getText().trim();
        String email  = emailField.getText().trim();
        String pass   = new String(passField.getPassword());

        // Validazioni base
        if (nome.isEmpty() || cogn.isEmpty() || cf.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "Compila tutti i campi.", "Campi mancanti", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cf.length() != 16) {
            JOptionPane.showMessageDialog(dialog, "Il Codice Fiscale deve avere 16 caratteri.", "CF non valido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!email.contains("@") || email.startsWith("@") || email.endsWith("@")) {
            JOptionPane.showMessageDialog(dialog, "Email non valida.", "Formato email", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Controller c = menu.getController();

        // 1) Controllo esistenza utente (per CF)
        boolean giaEsistente = false;
        giaEsistente = c.findClienteByCF(cf);

        if (giaEsistente) {
            JOptionPane.showMessageDialog(dialog,
                    "Sei già registrato. Effettua il login con la tua email e password.",
                    "Utente già presente", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
            new UserLogin(menu, owner);
            return;
        }

        // 2) Registrazione
        boolean ok = false;
        ok = c.registerCliente(cf, nome, cogn, indirizzo, email, pass);

        if (!ok) {
            JOptionPane.showMessageDialog(dialog,
                    "Registrazione non riuscita. Riprova più tardi.",
                    "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(dialog,
                "Registrazione completata! Ora effettua il login.",
                "Benvenuto!", JOptionPane.INFORMATION_MESSAGE);

        dialog.dispose();
        new UserLogin(menu, owner);
    }

    // ------- Helpers UI -------

    private static void addRow(JPanel form, GridBagConstraints gc, String label, JComponent field) {
        JLabel l = new JLabel(label + ":");
        l.setForeground(Color.WHITE);
        l.setHorizontalAlignment(SwingConstants.RIGHT);
        l.setPreferredSize(new Dimension(110, 26));

        gc.gridx = 0; gc.weightx = 0.2;
        form.add(l, gc);
        gc.gridx = 1; gc.weightx = 0.8;
        form.add(field, gc);
        gc.gridy++;
    }

    private static JTextField textField(int cols) {
        JTextField tf = new JTextField(cols);
        tf.setBackground(Color.DARK_GRAY);
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(6,8,6,8)
        ));
        return tf;
    }

    /** DocumentFilter per limitare la lunghezza e forzare maiuscolo (usato sul CF). */
    private static class LengthFilter extends DocumentFilter {
        private final int max;
        LengthFilter(int max) { this.max = max; }
        @Override public void insertString(FilterBypass fb, int off, String str, AttributeSet a) throws BadLocationException {
            if (str == null) return;
            str = str.toUpperCase();
            if (fb.getDocument().getLength() + str.length() <= max) {
                super.insertString(fb, off, str, a);
            }
        }
        @Override public void replace(FilterBypass fb, int off, int len, String str, AttributeSet a) throws BadLocationException {
            if (str == null) str = "";
            str = str.toUpperCase();
            if (fb.getDocument().getLength() - len + str.length() <= max) {
                super.replace(fb, off, len, str, a);
            }
        }
    }
}
