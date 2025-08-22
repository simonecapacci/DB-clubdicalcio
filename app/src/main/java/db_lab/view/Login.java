
package db_lab.view;

import db_lab.controller.Controller;
import db_lab.data.Cliente;
import db_lab.util.*;
import javax.swing.*;
import java.awt.*;

public class Login {
    private final JDialog dialog;
    private final JFrame frame;
    private final JuventusMenu menu;   // per getController()
    private final JTextField emailField = new JTextField();
    private final JPasswordField passField = new JPasswordField();
    private final JButton btnLogin  = UIUtils.primary("Accedi");
    private final JButton btnSignup = UIUtils.primary("Registrati");
    private final JButton btnBack   = UIUtils.primary("Indietro");
    private final UserPage userpage;

    /** Costruttore principale: passi il menu (per il controller) e il frame (owner della dialog). */
    public Login(JuventusMenu menu, JFrame ownerFrame) {
        this.menu = menu;
        this.frame = ownerFrame;
        this.dialog = new JDialog(ownerFrame, "Accedi", true); // modal
        buildUI(dialog.getContentPane());
        wireActions();
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(ownerFrame);
    }

    private static JFrame findFrame(Component c) {
        Window w = SwingUtilities.getWindowAncestor(c);
        return (w instanceof JFrame) ? (JFrame) w : new JFrame(); // fallback sicuro
    }

    private Controller ctrl() { return menu.getController(); }

    // --- UI & azioni (identiche) ---
    private void buildUI(Container root) { /* ... identico a prima ... */ }
    private void wireActions() {
        dialog.getRootPane().setDefaultButton(btnLogin);
        btnLogin.addActionListener(e -> doLoginCliente());
        btnSignup.addActionListener(e -> { dialog.setVisible(false); ctrl().openRegister(); });
        btnBack.addActionListener(e -> { dialog.dispose(); ctrl().showMenu(); });
    }
    private void doLoginCliente() {
        String email = emailField.getText().trim();
        String pass  = new String(passField.getPassword());
        if (email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "Inserisci email e password.", "Campi mancanti", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Cliente Cliente = ctrl().loginCliente(email, pass); // Optional<Cliente> o simile
        if (Cliente == null) {
            JOptionPane.showMessageDialog(dialog, "Credenziali non valide.", "Accesso negato", JOptionPane.ERROR_MESSAGE);
            return;
        } else{
            dialog.dispose();
        userpage = new UserPage(menu, frame, Cliente);
        this.goUserPage();
        }
        
    }

    public void resetAndShow() { emailField.setText(""); passField.setText(""); dialog.setVisible(true); }

    public void goUserPage() {
        userpage.setUp(); 
    }
}


