
package db_lab.view;

import db_lab.controller.Controller;
import db_lab.data.Cliente;
import db_lab.util.*;
import javax.swing.*;
import java.awt.*;

public class UserLogin extends Login{
    UserPage userpage;

    /** Costruttore principale: passi il menu (per il controller) e il frame (owner della dialog). */
    public UserLogin(JuventusMenu menu, JFrame ownerFrame) {
        super(menu, ownerFrame);
    }


    protected void doLogin() {
        String email = emailField.getText().trim();
        String pass  = new String(passField.getPassword());
        if (email.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "Inserisci email e password.", "Campi mancanti", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Cliente Cliente = super.ctrl().loginCliente(email, pass); // Optional<Cliente> o simile
        if (Cliente == null) {
            JOptionPane.showMessageDialog(dialog, "Credenziali non valide.", "Accesso negato", JOptionPane.ERROR_MESSAGE);
            return;
        } else{
            dialog.dispose();
            userpage = new UserPage(menu, frame, Cliente);
            this.goNextPage();
        }
        
    }

    public void goNextPage() {
        var cp = frame.getContentPane();
        cp.removeAll();
        userpage.setUp(); 
    }
}


