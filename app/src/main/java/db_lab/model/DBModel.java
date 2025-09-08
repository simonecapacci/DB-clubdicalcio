package db_lab.model;

import db_lab.data.AssistPersonali;
import db_lab.data.Calciatore;
import db_lab.data.Cliente;
import db_lab.data.Dirigente;
import db_lab.data.GoalPersonali;
import db_lab.data.Partita;
import db_lab.data.TopSeller;
import db_lab.data.Prodotto;
import db_lab.data.Calciatore;
import db_lab.data.BestClient;
import db_lab.data.MatchRevenue;
import db_lab.data.PurchaseItem;
import db_lab.data.OrderInfo;
import db_lab.data.OrderItem;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

// This is the real model implementation that uses the DAOs we've defined to
// actually load data from the underlying database.
//
// As you can see this model doesn't do too much except loading data from the
// database and keeping a cache of the loaded previews.
// A real model might be doing much more, but for the sake of the example we're
// keeping it simple.
//
public final class DBModel implements Model {

    private final Connection connection;

    public DBModel(Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
    }

    @Override
    public Cliente loginCliente(String email, String pass) {
        return Cliente.DAO.loginCliente(email, pass, connection);
    }

    @Override
    public List<AssistPersonali> getTopAssistmen() {
        return Calciatore.DAO.getTopAssistmen(connection);
    }

    @Override
    public List<GoalPersonali> getTopScorers() {
        return Calciatore.DAO.getTopScorers(connection);
    }

    @Override
    public Dirigente loginAdmin(String cf, String pass) {
        return Dirigente.DAO.loginAdmin(cf, pass, connection);
    }

    @Override
    public boolean findClienteByCF(String cf) {
        return Cliente.DAO.findClienteByCF(cf, connection);
    }

    @Override
    public boolean registerCliente(String cf, String nome, String cognome, String indirizzodispedizione, String email, String pass) {
        return Cliente.DAO.registerCliente(cf, nome, cognome, indirizzodispedizione, email, pass, connection);
    }

    // --- Admin: gestione dipendenti ---
    @Override
    public boolean findCalciatoreByCF(String cf) {
        return db_lab.data.Calciatore.DAO.findCalciatoreByCF(cf, connection);
    }

    @Override
    public boolean removeCalciatore(String cf) {
        return db_lab.data.Calciatore.DAO.removeCalciatore(cf, connection);
    }

    @Override
    public boolean registerCalciatore(String cf, String nome, String cognome, int numeroMaglia, int idContratto) {
        return db_lab.data.Calciatore.DAO.registerCalciatore(cf, nome, cognome, numeroMaglia, idContratto, connection);
    }

    @Override
    public boolean removeMembroStaff(String cf) {
        return db_lab.data.Membrostaff.DAO.removeMembroStaff(cf, connection);
    }

    @Override
    public boolean registerMembroStaff(String cf, String ruolo, String nome, String cognome, int idContratto) {
        return db_lab.data.Membrostaff.DAO.registerMembroStaff(cf, ruolo, nome, cognome, idContratto, connection);
    }

    @Override
    public boolean removeGuida(String cf) {
        return db_lab.data.Guida.DAO.removeGuida(cf, connection);
    }

    @Override
    public boolean registerGuida(String cf, String nome, String cognome, String turnoLavorativo, int idContratto) {
        return db_lab.data.Guida.DAO.registerGuida(cf, nome, cognome, turnoLavorativo, idContratto, connection);
    }

    // --- Inserimenti di supporto ---
    @Override
    public boolean addContratto(int idContratto, String dataStipulazione, String durata, int stipendio) {
        return db_lab.data.Contratto.DAO.addContratto(idContratto, dataStipulazione, durata, stipendio, connection);
    }

    @Override
    public boolean addTrasferimento(int idTrasferimento, String clubCoinvolto, int valoreTrasferimento,
                                    String durataPrestitoOrNull, String dataTrasferimento, String cf) {
        return db_lab.data.Trasferimento.DAO.addTrasferimento(idTrasferimento, clubCoinvolto, valoreTrasferimento,
                durataPrestitoOrNull, dataTrasferimento, cf, connection);
    }

    @Override
    public java.util.List<Partita> listPartite() {
        return db_lab.data.Partita.DAO.listAll(connection);
    }

    @Override
    public int countSpettatori(int idPartita) {
        return db_lab.data.Partita.DAO.countSpettatori(idPartita, connection);
    }

    @Override
    public java.util.Optional<TopSeller> getTopJerseySeller() {
        return java.util.Optional.ofNullable(db_lab.data.Calciatore.DAO.getTopJerseySeller(connection));
    }

    // --- Abbonamenti ---
    @Override
    public boolean addAbbonamento(String cf, String tipoAbbonamento, int anno) {
        return db_lab.data.Abbonamento.DAO.addAbbonamento(cf, tipoAbbonamento, anno, connection);
    }

    @Override
    public boolean hasAbbonamento(String cf, int anno) {
        return db_lab.data.Abbonamento.DAO.hasAbbonamento(cf, anno, connection);
    }

    @Override
    public float getScontoPercentuale(String cf, int anno) {
        return db_lab.data.Abbonamento.DAO.getScontoPercentuale(cf, anno, connection);
    }

    @Override
    public java.util.List<Prodotto> listProdotti() {
        return db_lab.data.Prodotto.DAO.listAll(connection);
    }

    @Override
    public java.util.List<Calciatore> listCalciatori() {
        return db_lab.data.Calciatore.DAO.listAll(connection);
    }

    @Override
    public java.util.List<BestClient> listBestClients() {
        return db_lab.data.BestClient.DAO.listTop(connection);
    }

    @Override
    public java.util.Optional<MatchRevenue> getMostProfitableMatch() {
        return java.util.Optional.ofNullable(db_lab.data.MatchRevenue.DAO.getMostProfitable(connection));
    }

    @Override
    public int finalizePurchase(String clienteCF, java.util.List<PurchaseItem> items) {
        if (clienteCF == null || clienteCF.isBlank() || items == null || items.isEmpty()) return -1;
        boolean oldAuto;
        try {
            oldAuto = connection.getAutoCommit();
        } catch (Exception e) { oldAuto = true; }
        try {
            connection.setAutoCommit(false);

            // New order id
            int newOrderId = 1;
            try (var ps = connection.prepareStatement("SELECT COALESCE(MAX(Codiceordine),0)+1 AS nextId FROM ordine");
                 var rs = ps.executeQuery()) {
                if (rs.next()) newOrderId = rs.getInt("nextId");
            }

            // Insert ordine
            String today = java.time.LocalDate.now().toString();
            try (var ps = db_lab.data.DAOUtils.prepare(connection,
                    "INSERT INTO ordine (Codiceordine, Data, Rimborsato, CF) VALUES (?, ?, 0, ?)",
                    newOrderId, today, clienteCF)) {
                ps.executeUpdate();
            }

            // Prepare statements
            try (var psTipo = connection.prepareStatement("SELECT Tipologia FROM prodotto WHERE Codiceprodotto=?");
                 var psPers = connection.prepareStatement(
                         "INSERT INTO articolo_personale (Codiceprodotto, CFCalciatore, Codiceordine) VALUES (?,?,?) " +
                         "ON DUPLICATE KEY UPDATE CFCalciatore=VALUES(CFCalciatore), Codiceordine=VALUES(Codiceordine)");
                 var psGen = connection.prepareStatement(
                         "INSERT INTO articolo_generale (Codiceprodotto, Codiceordine) VALUES (?,?) " +
                         "ON DUPLICATE KEY UPDATE Codiceordine=VALUES(Codiceordine)");
                 var psVis = connection.prepareStatement(
                         "INSERT INTO visita_guidata (Codiceprodotto, DataVisita, CFGuida, Codiceordine) VALUES (?,?,?,?) " +
                         "ON DUPLICATE KEY UPDATE DataVisita=VALUES(DataVisita), CFGuida=VALUES(CFGuida), Codiceordine=VALUES(Codiceordine)");
                 var psBigUpd = connection.prepareStatement(
                         "UPDATE biglietto SET Codiceordine=? WHERE Codiceprodotto=?");
                 var psGuidaByTurno = connection.prepareStatement(
                         "SELECT CF FROM guida WHERE LOWER(Turnolavorativo) = ? LIMIT 1");
            ) {
                for (PurchaseItem it : items) {
                    int code = it.codiceProdotto;
                    String type = it.tipologia;
                    // validate type from DB to avoid mismatches
                    String actualType = null;
                    psTipo.setInt(1, code);
                    try (var rs = psTipo.executeQuery()) { if (rs.next()) actualType = rs.getString(1); }
                    String t = (actualType != null && !actualType.isBlank()) ? actualType : type;
                    if ("Articolopersonale".equalsIgnoreCase(t)) {
                        psPers.setInt(1, code);
                        psPers.setString(2, it.cfCalciatore);
                        psPers.setInt(3, newOrderId);
                        psPers.executeUpdate();
                    } else if ("Articologenerale".equalsIgnoreCase(t)) {
                        psGen.setInt(1, code);
                        psGen.setInt(2, newOrderId);
                        psGen.executeUpdate();
                    } else if ("Visitaguidata".equalsIgnoreCase(t)) {
                        String date = (it.dataVisita != null ? it.dataVisita : java.time.LocalDate.now().plusDays(1).toString());
                        String turno = (it.slotVisita != null ? it.slotVisita.toLowerCase() : "mattina");
                        psGuidaByTurno.setString(1, turno);
                        String cfGuida = null;
                        try (var rsG = psGuidaByTurno.executeQuery()) {
                            if (rsG.next()) cfGuida = rsG.getString(1);
                        }
                        if (cfGuida == null || cfGuida.isBlank()) {
                            throw new db_lab.data.DAOException(new IllegalStateException("Nessuna guida disponibile per il turno: " + turno));
                        }
                        psVis.setInt(1, code);
                        psVis.setString(2, date);
                        psVis.setString(3, cfGuida);
                        psVis.setInt(4, newOrderId);
                        psVis.executeUpdate();
                    } else if ("Biglietto".equalsIgnoreCase(t)) {
                        psBigUpd.setInt(1, newOrderId);
                        psBigUpd.setInt(2, code);
                        int up = psBigUpd.executeUpdate();
                        if (up == 0) {
                            throw new db_lab.data.DAOException(new IllegalStateException("Nessun biglietto disponibile per Codiceprodotto=" + code));
                        }
                    } else {
                        // unknown type -> ignore or treat as general
                        psGen.setInt(1, code);
                        psGen.setInt(2, newOrderId);
                        psGen.executeUpdate();
                    }
                }
            }

            connection.commit();
            try { connection.setAutoCommit(oldAuto); } catch (Exception ignore) {}
            return newOrderId;
        } catch (Exception e) {
            try { connection.rollback(); } catch (Exception ignore) {}
            try { connection.setAutoCommit(oldAuto); } catch (Exception ignore) {}
            throw new db_lab.data.DAOException(e);
        }
    }

    @Override
    public java.util.List<OrderInfo> listRecentOrders(String clienteCF) {
        java.util.List<OrderInfo> list = new java.util.ArrayList<>();
        try (
            var ps = db_lab.data.DAOUtils.prepare(connection, db_lab.data.Queries.LIST_RECENT_ORDERS_FOR_CLIENT, clienteCF);
            var rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                int id = rs.getInt("Codiceordine");
                String data = rs.getString("Data");
                list.add(new OrderInfo(id, data));
            }
        } catch (java.sql.SQLException e) {
            throw new db_lab.data.DAOException(e);
        }
        return list;
    }

    @Override
    public java.util.List<OrderItem> listOrderItems(int codiceOrdine) {
        java.util.List<OrderItem> items = new java.util.ArrayList<>();
        try (
            var ps = db_lab.data.DAOUtils.prepare(connection, db_lab.data.Queries.LIST_ORDER_ITEMS, codiceOrdine);
            var rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                String nome = rs.getString("Nome");
                String tip = rs.getString("Tipologia");
                double imp = rs.getDouble("Importo");
                items.add(new OrderItem(nome, tip, imp));
            }
        } catch (java.sql.SQLException e) {
            throw new db_lab.data.DAOException(e);
        }
        return items;
    }

    @Override
    public db_lab.util.Pair<Double, Double> getOrderTotals(int codiceOrdine) {
        try (
            var ps = db_lab.data.DAOUtils.prepare(connection, db_lab.data.Queries.ORDER_TOTAL_WITH_DISCOUNT, codiceOrdine);
            var rs = ps.executeQuery();
        ) {
            if (rs.next()) {
                double lordo = rs.getDouble("TotaleLordo");
                double netto = rs.getDouble("TotaleNetto");
                return db_lab.util.Pair.of(lordo, netto);
            }
            return db_lab.util.Pair.of(0.0, 0.0);
        } catch (java.sql.SQLException e) {
            throw new db_lab.data.DAOException(e);
        }
    }

    @Override
    public boolean refundOrder(String clienteCF, int codiceOrdine) {
        boolean oldAuto;
        try { oldAuto = connection.getAutoCommit(); } catch (Exception e) { oldAuto = true; }
        try {
            connection.setAutoCommit(false);
            // clear items
            try (var ps1 = db_lab.data.DAOUtils.prepare(connection, db_lab.data.Queries.REFUND_DELETE_ARTICOLO_PERSONALE, codiceOrdine)) { ps1.executeUpdate(); }
            try (var ps2 = db_lab.data.DAOUtils.prepare(connection, db_lab.data.Queries.REFUND_DELETE_ARTICOLO_GENERALE, codiceOrdine)) { ps2.executeUpdate(); }
            try (var ps3 = db_lab.data.DAOUtils.prepare(connection, db_lab.data.Queries.REFUND_CLEAR_BIGLIETTO, codiceOrdine)) { ps3.executeUpdate(); }
            try (var ps4 = db_lab.data.DAOUtils.prepare(connection, db_lab.data.Queries.REFUND_CLEAR_VISITA_GUIDATA, codiceOrdine)) { ps4.executeUpdate(); }
            // delete order (scoped by CF)
            int deleted;
            try (var ps5 = db_lab.data.DAOUtils.prepare(connection, db_lab.data.Queries.REFUND_DELETE_ORDINE, codiceOrdine, clienteCF)) {
                deleted = ps5.executeUpdate();
            }
            if (deleted == 0) {
                connection.rollback();
                try { connection.setAutoCommit(oldAuto); } catch (Exception ignore) {}
                return false;
            }
            connection.commit();
            try { connection.setAutoCommit(oldAuto); } catch (Exception ignore) {}
            return true;
        } catch (Exception e) {
            try { connection.rollback(); } catch (Exception ignore) {}
            try { connection.setAutoCommit(oldAuto); } catch (Exception ignore) {}
            throw new db_lab.data.DAOException(e);
        }
    }

    @Override
    public boolean orderHasPastVisit(int codiceOrdine) {
        try (
            var ps = db_lab.data.DAOUtils.prepare(connection, db_lab.data.Queries.ORDER_HAS_PAST_VISIT, codiceOrdine);
            var rs = ps.executeQuery();
        ) {
            return rs.next();
        } catch (java.sql.SQLException e) {
            throw new db_lab.data.DAOException(e);
        }
    }

}
