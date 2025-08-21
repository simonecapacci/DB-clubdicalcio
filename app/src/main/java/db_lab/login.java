package db_lab;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;

public class login {

    private Menu menu;
    private JFrame mainFrame;
    private Persona persona;
    private UserPage userPage;
    private Runnable onClose;

        public Login(Menu menu, JFrame mainFrame, Runnable onClose) {
        this.menu = menu;
        this.mainFrame = mainFrame;
        this.onClose = onClose;
    }

      public JFrame setUp(/* Runnable onClose */) {

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        JPanel backPanel = new JPanel();
        backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.Y_AXIS));

        JPanel informationPanel = new JPanel(new GridLayout(2, 2, 0, 0));
        informationPanel.setMaximumSize(new Dimension(500, 50));
        informationPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        //Pannello con griglia 2x2 per etichette e campi CF/password.


        JLabel titleLabel = new JLabel("Login", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(36.0f));
        titleLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(40, 0, 20, 0));
        main.add(titleLabel, BorderLayout.NORTH);
        main.add(Box.createVerticalStrut(200));

        // Etichetta titolo “Login” centrata e stilizzata. Spazio verticale.


        JLabel cfLabel = new JLabel("CF:");
        JTextField cfField = new JTextField(16);
        cfField.setMinimumSize(new Dimension(150, 15));
        ((AbstractDocument) cfField.getDocument()).setDocumentFilter(new LimitDocumentFilter(16));
        cfLabel.setAlignmentX(JLabel.RIGHT);
        cfField.setAlignmentX(JLabel.LEFT_ALIGNMENT);
        informationPanel.add(cfLabel);
        informationPanel.add(cfField);

        //Campo codice fiscale, con limite massimo di 16 caratteri tramite filtro.

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);
        passField.setMaximumSize(new Dimension(300, 30));
        informationPanel.add(passLabel);
        informationPanel.add(passField);

        //Campo password (mascherato).

        main.add(Box.createVerticalStrut(25));
        main.add(informationPanel);
        main.add(Box.createVerticalStrut(170));

        //Spaziatura verticale tra i pannelli.


        JButton confirmButton = new JButton("Accedi");
        confirmButton.setMinimumSize(new Dimension(200, 50));
        confirmButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        main.add(confirmButton);

        // Bottone “Accedi” centrato e dimensionato.

        confirmButton.addActionListener(e -> {
            String cf = cfField.getText();
            char[] passChars = passField.getPassword();
            String pass = new String(passChars);

                    if (cf.isEmpty() && pass.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Compila tutti i campi prima dell'accesso", 
                    "Campi mancanti", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (cf.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Campo CF mancante", 
                    "Campi mancanti", 
                    JOptionPane.WARNING_MESSAGE);
            } else if (pass.isEmpty()) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Campo Password mancante", 
                    "Campi mancanti", 
                    JOptionPane.WARNING_MESSAGE);

    // Messaggi d’errore se i campi non sono compilati.

                } else {
                persona = menu.getController().findPersona(cf);

    //Cerca l’utente nel database tramite il controller

                              if (persona != null) {
                    if (persona.password.equals(pass) && persona.utente == true) {
                        userPage = new UserPage(menu, mainFrame, persona, onClose);
                        this.goUserPage(persona);
                    } else {
                        JOptionPane.showMessageDialog(
                        null,
                        "La password e il CF non corrispondono", 
                        "Dati non corretti",
                        JOptionPane.WARNING_MESSAGE);
                        passField.setText("");
                    }
      // Se l’utente esiste ed è un “utente abilitato”, accede alla pagina utente; altrimenti errore.

                      } else {
                    JOptionPane.showMessageDialog(
                    null, 
                    "La persona inserita non esiste", 
                    "Persona mancante", 
                    JOptionPane.WARNING_MESSAGE);
                    cfField.setText("");
                    passField.setText("");
                }
            }
        });
    // Errore se il CF non è trovato nel database

            JButton backButton = new JButton("Indietro");
        backButton.setAlignmentX(JButton.LEFT_ALIGNMENT);
        backButton.setAlignmentY(JButton.TOP_ALIGNMENT);
        backPanel.add(backButton);
        backButton.addActionListener(e -> {this.back();});
    // Bottone “Indietro” che chiama il metodo back() per tornare al menu.

            mainFrame.getContentPane().add(main, BorderLayout.CENTER);
        mainFrame.getContentPane().add(backPanel, BorderLayout.WEST);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);

    //Aggiunge i pannelli al frame e lo visualizza.

         mainFrame.addWindowListener(
            (WindowListener) new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    onClose.run();
                    System.exit(0);
                }
            }
        );

    //Listener per chiusura finestra: esegue l'azione onClose e chiude l’app.
            return mainFrame;
    }
    // Ritorna il frame aggiornato con la schermata di login.

        public void back() {
        var cp = mainFrame.getContentPane();
        cp.removeAll();
        mainFrame = menu.setUp(onClose);
    }
    // Metodo per tornare al menu iniziale.

    public void goUserPage(Persona persona) {
        var cp = mainFrame.getContentPane();
        cp.removeAll();
        userPage.setUp(); 
    }
    //Metodo per aprire la pagina utente dopo il login.
    }


