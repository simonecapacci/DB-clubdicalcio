package db_lab.view;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import db_lab.data.Cliente;
import db_lab.data.Dirigente;

public class AdminLogin extends Login {
    AdminPage adminpage;
    public AdminLogin(JuventusMenu menu, JFrame frame){
        super(menu, frame);
    }
    @Override
    protected void doLogin() {
        String cf = emailField.getText().trim();
        String pass  = new String(passField.getPassword());
        if (cf.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "Inserisci cf e password.", "Campi mancanti", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Dirigente admin = super.ctrl().loginAdmin(cf, pass); // Optional<Cliente> o simile
        if (admin == null) {
            JOptionPane.showMessageDialog(dialog, "Credenziali non valide.", "Accesso negato", JOptionPane.ERROR_MESSAGE);
            return;
        } else{
            dialog.dispose();
            adminpage = new AdminPage(menu, frame, admin);
            this.goNextPage();
        }
        
    }

    @Override
    protected void goNextPage() {
        var cp = frame.getContentPane();
        cp.removeAll();
        adminpage.setUp();
    }
    
}
