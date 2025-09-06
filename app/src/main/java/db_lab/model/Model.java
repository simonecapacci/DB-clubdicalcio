package db_lab.model;

import db_lab.data.AssistPersonali;
import db_lab.data.Calciatore;
import db_lab.data.Cliente;
import db_lab.data.Dirigente;
import db_lab.data.GoalPersonali;
import db_lab.data.Partita;
import db_lab.data.TopSeller;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface Model {

    // Create a model that connects to a database using the given connection.
    //
    static Model fromConnection(Connection connection) {
        return new DBModel(connection);
    }

    Cliente loginCliente(String email, String pass);

    List<AssistPersonali> getTopAssistmen();

    List<GoalPersonali> getTopScorers();

    Dirigente loginAdmin(String cf, String pass);

    boolean findClienteByCF(String cf);

    boolean registerCliente(String cf, String nome, String cognome, String indirizzodispedizione, String email, String pass);

    // --- Admin: gestione dipendenti ---
    boolean findCalciatoreByCF(String cf);
    boolean removeCalciatore(String cf);
    boolean registerCalciatore(String cf, String nome, String cognome, int numeroMaglia, int idContratto);

    boolean removeMembroStaff(String cf);
    boolean registerMembroStaff(String cf, String ruolo, String nome, String cognome, int idContratto);

    boolean removeGuida(String cf);
    boolean registerGuida(String cf, String nome, String cognome, String turnoLavorativo, int idContratto);

    // Inserimenti di supporto
    boolean addContratto(int idContratto, String dataStipulazione, String durata, int stipendio);
    boolean addTrasferimento(int idTrasferimento, String clubCoinvolto, int valoreTrasferimento,
                             String durataPrestitoOrNull, String dataTrasferimento, String cf);

    // Partite & Presenze
    java.util.List<Partita> listPartite();
    int countSpettatori(int idPartita);

    java.util.Optional<TopSeller> getTopJerseySeller();

    // Abbonamenti
    boolean addAbbonamento(String cf, String tipoAbbonamento, int anno);
    boolean hasAbbonamento(String cf, int anno);
}
