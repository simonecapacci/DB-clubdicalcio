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

}
