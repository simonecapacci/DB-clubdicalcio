
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
    private UserPage userpage;

    /** Costruttore principale: passi il menu (per il controller) e il frame (owner della dialog). */
    public Login(JuventusMenu menu, JFrame ownerFrame) {
        this.menu = menu;
        this.frame = ownerFrame;
        this.dialog = new JDialog(ownerFrame, "Accedi", true); // modal
        dialog.setContentPane(buildUI());
        wireActions();
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(ownerFrame);
        dialog.setVisible(true);
    }

    private static JFrame findFrame(Component c) {
        Window w = SwingUtilities.getWindowAncestor(c);
        return (w instanceof JFrame) ? (JFrame) w : new JFrame(); // fallback sicuro
    }

    private Controller ctrl() { return menu.getController(); }

    // --- UI & azioni (identiche) ---
    private JPanel buildUI() {
    var panel = new JPanel(new GridBagLayout());
    var gbc = new GridBagConstraints();
    gbc.insets = new Insets(8, 10, 8, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Email"), gbc);
    gbc.gridx = 1; emailField.setColumns(20); panel.add(emailField, gbc);
    gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Password"), gbc);
    gbc.gridx = 1; passField.setColumns(20); panel.add(passField, gbc);
    gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; panel.add(btnLogin, gbc);
    return panel;
}

    private void wireActions() {
        dialog.getRootPane().setDefaultButton(btnLogin);
        btnLogin.addActionListener(e -> doLoginCliente());
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
        var cp = frame.getContentPane();
        cp.removeAll();
        userpage.setUp(); 
    }
}


