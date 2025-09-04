package db_lab.view;

import db_lab.util.UIUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Pagina di gestione dipendenti. Mostra un JTabbedPane con schede per ogni tipo.
 * Per ora è implementata la scheda "Calciatore" con i campi richiesti e i bottoni
 * "Registra" e "Rimuovi" in fondo alla scheda.
 */
public class ManageStaffPage {
    private final JuventusMenu menu;
    private final JFrame frame;
    private final AdminPage adminPage; // per tornare indietro

    // Top
    private final JButton btnBack = UIUtils.primary("Indietro");
    private final JLabel title = new JLabel("Gestione dipendenti");

    // Calciatore: campi
    private final JTextField tfCf = new JTextField();
    private final JTextField tfNumeroMaglia = new JTextField();
    private final JTextField tfNome = new JTextField();
    private final JTextField tfCognome = new JTextField();
    private final JTextField tfIdContratto = new JTextField();

    private final JButton btnRegistraCalciatore = UIUtils.primary("Registra");
    private final JButton btnRimuoviCalciatore = UIUtils.primary("Rimuovi");

    // Membro Staff: campi
    private final JTextField tfStaffCf = new JTextField();
    private final JTextField tfStaffRuolo = new JTextField();
    private final JTextField tfStaffNome = new JTextField();
    private final JTextField tfStaffCognome = new JTextField();
    private final JTextField tfStaffIdContratto = new JTextField();

    private final JButton btnRegistraStaff = UIUtils.primary("Registra");
    private final JButton btnRimuoviStaff = UIUtils.primary("Rimuovi");

    // Guida: campi
    private final JTextField tfGuidaCf = new JTextField();
    private final JTextField tfGuidaNome = new JTextField();
    private final JTextField tfGuidaCognome = new JTextField();
    private final JTextField tfGuidaTurno = new JTextField();
    private final JTextField tfGuidaIdContratto = new JTextField();

    private final JButton btnRegistraGuida = UIUtils.primary("Registra");
    private final JButton btnRimuoviGuida = UIUtils.primary("Rimuovi");

    public ManageStaffPage(JuventusMenu menu, AdminPage adminPage, JFrame frame) {
        this.menu = menu;
        this.frame = frame;
        this.adminPage = adminPage;
        configureLookAndFeel();
        wireActions();
    }

    public void setUp() {
        Container cp = frame.getContentPane();
        cp.removeAll();
        cp.setLayout(new BorderLayout());
        cp.setBackground(Color.BLACK);

        // North: back + title
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

        // Center: tabbed pane
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(Color.BLACK);
        tabs.setForeground(Color.WHITE); // ensure tab titles are visible on dark bg

        tabs.addTab("Calciatore", buildCalciatoreTab());
        tabs.addTab("Membro Staff", buildMembroStaffTab());
        tabs.addTab("Guida", buildGuidaTab());

        // Make all tab titles readable on dark background
        for (int i = 0; i < tabs.getTabCount(); i++) {
            tabs.setForegroundAt(i, Color.WHITE);
        }

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(Color.BLACK);
        center.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        center.add(tabs, BorderLayout.CENTER);
        cp.add(center, BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    private JPanel buildCalciatoreTab() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        // CF
        addLabeledField(panel, gbc, row++, new JLabel("CF"), tfCf);
        // Numero di maglia
        addLabeledField(panel, gbc, row++, new JLabel("Numero di maglia"), tfNumeroMaglia);
        // Nome
        addLabeledField(panel, gbc, row++, new JLabel("Nome"), tfNome);
        // Cognome
        addLabeledField(panel, gbc, row++, new JLabel("Cognome"), tfCognome);
        // IDContratto
        addLabeledField(panel, gbc, row++, new JLabel("IDContratto"), tfIdContratto);

        // Spaziatore elastico
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2; gbc.weightx = 1; gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(Box.createGlue(), gbc);

        // Barra bottoni in basso
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 6));
        buttons.setBackground(Color.BLACK);
        buttons.add(btnRimuoviCalciatore);
        buttons.add(btnRegistraCalciatore);

        gbc.gridx = 0; gbc.gridy = ++row; gbc.gridwidth = 2; gbc.weightx = 0; gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttons, gbc);

        return panel;
    }

    private JPanel buildMembroStaffTab() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        addLabeledField(panel, gbc, row++, new JLabel("CF"), tfStaffCf);
        addLabeledField(panel, gbc, row++, new JLabel("Ruolo"), tfStaffRuolo);
        addLabeledField(panel, gbc, row++, new JLabel("Nome"), tfStaffNome);
        addLabeledField(panel, gbc, row++, new JLabel("Cognome"), tfStaffCognome);
        addLabeledField(panel, gbc, row++, new JLabel("ID Contratto"), tfStaffIdContratto);

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2; gbc.weightx = 1; gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(Box.createGlue(), gbc);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 6));
        buttons.setBackground(Color.BLACK);
        buttons.add(btnRimuoviStaff);
        buttons.add(btnRegistraStaff);

        gbc.gridx = 0; gbc.gridy = ++row; gbc.gridwidth = 2; gbc.weightx = 0; gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttons, gbc);

        return panel;
    }

    private JPanel buildGuidaTab() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        addLabeledField(panel, gbc, row++, new JLabel("CF"), tfGuidaCf);
        addLabeledField(panel, gbc, row++, new JLabel("Nome"), tfGuidaNome);
        addLabeledField(panel, gbc, row++, new JLabel("Cognome"), tfGuidaCognome);
        addLabeledField(panel, gbc, row++, new JLabel("Turno Lavorativo"), tfGuidaTurno);
        addLabeledField(panel, gbc, row++, new JLabel("ID Contratto"), tfGuidaIdContratto);

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2; gbc.weightx = 1; gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(Box.createGlue(), gbc);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 6));
        buttons.setBackground(Color.BLACK);
        buttons.add(btnRimuoviGuida);
        buttons.add(btnRegistraGuida);

        gbc.gridx = 0; gbc.gridy = ++row; gbc.gridwidth = 2; gbc.weightx = 0; gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttons, gbc);

        return panel;
    }

    private void addLabeledField(JPanel parent, GridBagConstraints gbc, int row, JLabel label, JTextField field) {
        label.setForeground(Color.WHITE);
        field.setColumns(18);

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 0; gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        parent.add(label, gbc);

        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 1; gbc.weightx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        parent.add(field, gbc);
    }

    private void configureLookAndFeel() {
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
    }

    private void wireActions() {
        btnBack.addActionListener(e -> adminPage.setUp());

        btnRegistraCalciatore.addActionListener(e -> {
            var controller = menu.getController();
            String cf = tfCf.getText().trim();
            String nome = tfNome.getText().trim();
            String cognome = tfCognome.getText().trim();
            String numMagliaStr = tfNumeroMaglia.getText().trim();
            String idContrattoStr = tfIdContratto.getText().trim();
            if (cf.isEmpty() || nome.isEmpty() || cognome.isEmpty() || numMagliaStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Compila tutti i campi per registrare il calciatore.");
                return;
            }
            int numeroMaglia, idContratto;
            try {
                numeroMaglia = Integer.parseInt(numMagliaStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Numero di maglia deve essere un numero intero.");
                return;
            }
            // 1) Prima chiedi dettagli trasferimento
            TransferData transfer = promptTransferDetails(cf);
            if (transfer == null) return; // annullato

            // 2) Poi chiedi dettagli contratto (solo per registrazione)
            Integer defaultIdContr = null;
            try { defaultIdContr = Integer.parseInt(idContrattoStr); } catch (Exception ignore) {}
            ContractData contract = promptContractDetails(defaultIdContr);
            if (contract == null) return; // annullato
            idContratto = contract.idContratto;
            // Aggiorna anche il campo visivo per coerenza
            tfIdContratto.setText(String.valueOf(idContratto));

            // Persisti contratto prima del giocatore (FK)
            boolean okContr = controller.addContratto(contract.idContratto, contract.data, contract.durata, contract.stipendio);
            if (!okContr) {
                JOptionPane.showMessageDialog(frame, "Salvataggio contratto fallito.");
                return;
            }

            // Registra calciatore
            boolean okPlayer = controller.registerCalciatore(cf, nome, cognome, numeroMaglia, idContratto);
            if (!okPlayer) {
                JOptionPane.showMessageDialog(frame, "Registrazione calciatore fallita.");
                return;
            }

            // Infine persisti il trasferimento (entrata)
            boolean okTrans = controller.addTrasferimento(transfer.idTrasferimento, transfer.club, transfer.valore,
                    transfer.durataPrestitoOrNull, transfer.data, cf);
            JOptionPane.showMessageDialog(frame, (okContr && okPlayer && okTrans) ?
                    "Calciatore registrato con successo." : "Registrazione completata parzialmente (trasferimento non salvato).");
        });

        btnRimuoviCalciatore.addActionListener(e -> {
            var controller = menu.getController();
            String cf = tfCf.getText().trim();
            if (cf.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Inserire CF per rimuovere un calciatore");
                return;
            }
            // Verifica esistenza calciatore prima di qualsiasi dialog
            boolean exists = controller.findCalciatoreByCF(cf);
            if (!exists) {
                JOptionPane.showMessageDialog(frame, "Il giocatore non esiste");
                return;
            }
            // Poi chiedi dettagli trasferimento (uscita)
            TransferData transfer = promptTransferDetails(cf);
            if (transfer == null) return; // annullato
            // Salva trasferimento in uscita prima di rimuovere
            boolean okTrans = controller.addTrasferimento(transfer.idTrasferimento, transfer.club, transfer.valore,
                    transfer.durataPrestitoOrNull, transfer.data, cf);
            if (!okTrans) {
                JOptionPane.showMessageDialog(frame, "Salvataggio trasferimento fallito. Operazione annullata.");
                return;
            }
            boolean ok = controller.removeCalciatore(cf);
            JOptionPane.showMessageDialog(frame, ok ? "Calciatore rimosso." : "Nessun calciatore rimosso.");
        });

        btnRegistraStaff.addActionListener(e -> {
            var controller = menu.getController();
            String cf = tfStaffCf.getText().trim();
            String ruolo = tfStaffRuolo.getText().trim();
            String nome = tfStaffNome.getText().trim();
            String cognome = tfStaffCognome.getText().trim();
            String idContrattoStr = tfStaffIdContratto.getText().trim();
            if (cf.isEmpty() || ruolo.isEmpty() || nome.isEmpty() || cognome.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Compila tutti i campi per registrare il membro dello staff.");
                return;
            }
            Integer defaultId = null;
            try { defaultId = idContrattoStr.isBlank() ? null : Integer.parseInt(idContrattoStr); } catch (NumberFormatException ignore) {}
            ContractData contract = promptContractDetails(defaultId);
            if (contract == null) return;
            int idContratto = contract.idContratto;
            tfStaffIdContratto.setText(String.valueOf(idContratto));

            boolean okContr = controller.addContratto(contract.idContratto, contract.data, contract.durata, contract.stipendio);
            if (!okContr) {
                JOptionPane.showMessageDialog(frame, "Salvataggio contratto fallito.");
                return;
            }
            boolean ok = controller.registerMembroStaff(cf, ruolo, nome, cognome, idContratto);
            JOptionPane.showMessageDialog(frame, ok ? "Membro staff registrato con successo." : "Registrazione membro staff fallita.");
        });

        btnRimuoviStaff.addActionListener(e -> {
            var controller = menu.getController();
            String cf = tfStaffCf.getText().trim();
            if (cf.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Inserire CF per rimuovere un membro staff");
                return;
            }
            boolean ok = controller.removeMembroStaff(cf);
            JOptionPane.showMessageDialog(frame, ok ? "Membro staff rimosso." : "Nessun membro staff rimosso.");
        });

        btnRegistraGuida.addActionListener(e -> {
            var controller = menu.getController();
            String cf = tfGuidaCf.getText().trim();
            String nome = tfGuidaNome.getText().trim();
            String cognome = tfGuidaCognome.getText().trim();
            String turnoStr = tfGuidaTurno.getText().trim();
            String idContrattoStr = tfGuidaIdContratto.getText().trim();
            if (cf.isEmpty() || nome.isEmpty() || cognome.isEmpty() || turnoStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Compila tutti i campi per registrare la guida.");
                return;
            }

            Integer defaultId = null;
            try { defaultId = idContrattoStr.isBlank() ? null : Integer.parseInt(idContrattoStr); } catch (NumberFormatException ignore) {}
            ContractData contract = promptContractDetails(defaultId);
            if (contract == null) return;
            int idContratto = contract.idContratto;
            tfGuidaIdContratto.setText(String.valueOf(idContratto));
            
            //data in formato "year-mo-da"
            boolean okContr = controller.addContratto(contract.idContratto, contract.data, contract.durata, contract.stipendio);
            if (!okContr) {
                JOptionPane.showMessageDialog(frame, "Salvataggio contratto fallito.");
                return;
            }
            boolean ok = controller.registerGuida(cf, nome, cognome, turnoStr, idContratto);
            JOptionPane.showMessageDialog(frame, ok ? "Guida registrata con successo." : "Registrazione guida fallita.");
        });

        btnRimuoviGuida.addActionListener(e -> {
            var controller = menu.getController();
            String cf = tfGuidaCf.getText().trim();
            if (cf.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Inserire CF per rimuovere una guida");
                return;
            }
            boolean ok = controller.removeGuida(cf);
            JOptionPane.showMessageDialog(frame, ok ? "Guida rimossa." : "Nessuna guida rimossa.");
        });
    }

    // --- Dialog helpers ---
    private TransferData promptTransferDetails(String cf) {
        JTextField tfId = new JTextField();
        JTextField tfClub = new JTextField();
        JTextField tfValore = new JTextField();
        JTextField tfData = new JTextField();
        JTextField tfDurataPrestito = new JTextField(); // opzionale

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int row = 0;

        addLabeledField(panel, gbc, row++, whiteLabel("ID Trasferimento"), tfId);
        addLabeledField(panel, gbc, row++, whiteLabel("Club coinvolto"), tfClub);
        addLabeledField(panel, gbc, row++, whiteLabel("Valore trasferimento"), tfValore);
        addLabeledField(panel, gbc, row++, whiteLabel("Data trasferimento"), tfData);
        addLabeledField(panel, gbc, row++, whiteLabel("Durata prestito (opzionale)"), tfDurataPrestito);

        while (true) {
            int res = JOptionPane.showConfirmDialog(frame, panel, "Dettagli trasferimento (CF: " + cf + ")",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (res != JOptionPane.OK_OPTION) return null;
            String idStr = tfId.getText().trim();
            String club = tfClub.getText().trim();
            String valStr = tfValore.getText().trim();
            String data = tfData.getText().trim();
            String durata = tfDurataPrestito.getText().trim();
            if (idStr.isEmpty() || club.isEmpty() || valStr.isEmpty() || data.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Compila ID, Club, Valore, Data.");
                continue;
            }
            try {
                int id = Integer.parseInt(idStr);
                int valore = Integer.parseInt(valStr);
                return new TransferData(id, club, valore, data, durata.isBlank() ? null : durata);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "ID Trasferimento e Valore devono essere numeri interi.");
            }
        }
    }

    private ContractData promptContractDetails(Integer defaultId) {
        JTextField tfId = new JTextField(defaultId == null ? "" : String.valueOf(defaultId));
        JTextField tfData = new JTextField();
        JTextField tfDurata = new JTextField();
        JTextField tfStipendio = new JTextField();

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int row = 0;

        addLabeledField(panel, gbc, row++, whiteLabel("ID Contratto"), tfId);
        addLabeledField(panel, gbc, row++, whiteLabel("Data stipulazione"), tfData);
        addLabeledField(panel, gbc, row++, whiteLabel("Durata"), tfDurata);
        addLabeledField(panel, gbc, row++, whiteLabel("Stipendio"), tfStipendio);

        while (true) {
            int res = JOptionPane.showConfirmDialog(frame, panel, "Dettagli contratto",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (res != JOptionPane.OK_OPTION) return null;
            String idStr = tfId.getText().trim();
            String data = tfData.getText().trim();
            String durata = tfDurata.getText().trim();
            String stipendioStr = tfStipendio.getText().trim();
            if (idStr.isEmpty() || data.isEmpty() || durata.isEmpty() || stipendioStr.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Compila tutti i campi del contratto.");
                continue;
            }
            try {
                int id = Integer.parseInt(idStr);
                int stipendio = Integer.parseInt(stipendioStr);
                return new ContractData(id, data, durata, stipendio);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "ID Contratto e Stipendio devono essere numeri interi.");
            }
        }
    }

    private JLabel whiteLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(Color.WHITE);
        return l;
    }

    private static final class TransferData {
        final int idTrasferimento; final String club; final int valore; final String data; final String durataPrestitoOrNull;
        TransferData(int idTrasferimento, String club, int valore, String data, String durataPrestitoOrNull) {
            this.idTrasferimento = idTrasferimento; this.club = club; this.valore = valore; this.data = data; this.durataPrestitoOrNull = durataPrestitoOrNull;
        }
    }

    private static final class ContractData {
        final int idContratto; final String data; final String durata; final int stipendio;
        ContractData(int idContratto, String data, String durata, int stipendio) {
            this.idContratto = idContratto; this.data = data; this.durata = durata; this.stipendio = stipendio;
        }
    }
}
