package db_lab.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import db_lab.controller.Controller;
import db_lab.util.UIUtils;

public abstract class Login {
    protected final JDialog dialog;
    protected final JFrame frame;
    protected final JuventusMenu menu;   // per getController()
    protected final JTextField emailField = new JTextField();
    protected final JPasswordField passField = new JPasswordField();
    protected final JButton btnLogin  = UIUtils.primary("Accedi");

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
    protected Controller ctrl() { 
        return menu.getController(); 
    }

    // --- UI & azioni (identiche) ---
    private JPanel buildUI() {
    var panel = new JPanel(new GridBagLayout());
    var gbc = new GridBagConstraints();
    gbc.insets = new Insets(8, 10, 8, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("ID"), gbc);
    gbc.gridx = 1; emailField.setColumns(20); panel.add(emailField, gbc);
    gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Password"), gbc);
    gbc.gridx = 1; passField.setColumns(20); panel.add(passField, gbc);
    gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; panel.add(btnLogin, gbc);
    return panel;
    }
    private void wireActions() {
        dialog.getRootPane().setDefaultButton(btnLogin);
        btnLogin.addActionListener(e -> doLogin());
    }
    protected abstract void doLogin();
    protected abstract void goNextPage();
}
