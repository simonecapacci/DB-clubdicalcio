package db_lab.controller;

import db_lab.view.JuventusMenu;

import java.util.List;
import java.util.Objects;

import javax.swing.JFrame;

import db_lab.data.*;
import db_lab.model.*;

public class Controller {
    private final Model model;

    public Controller(Model model) {
        Objects.requireNonNull(model, "Controller created with null model");
        this.model = model;
    }

    private void onClose(){ /* eventuale cleanup */ }

    public Cliente loginCliente(String email, String pass) {
        return this.model.loginCliente(email, pass);
    }
    
    public List<GoalPersonali> getTopScorers(){
        return this.model.getTopScorers();
    }

    public List<AssistPersonali> getTopAssistmen(){
        return this.model.getTopAssistmen();
    }

    public Dirigente loginAdmin(String cf, String pass) {
        return this.model.loginAdmin(cf,pass);
    }

    public boolean findClienteByCF(String cf){
        return this.model.findClienteByCF(cf);
    }

    public boolean registerCliente(String cf, String nome, String cognome, String indirizzodispedizione, String email, String pass){
        return this.model.registerCliente(cf, nome, cognome, indirizzodispedizione, email, pass);
    }

    // --- Admin: gestione dipendenti ---
    public boolean findCalciatoreByCF(String cf) {
        return this.model.findCalciatoreByCF(cf);
    }
    public boolean removeCalciatore(String cf) {
        return this.model.removeCalciatore(cf);
    }
    public boolean registerCalciatore(String cf, String nome, String cognome, int numeroMaglia, int idContratto) {
        return this.model.registerCalciatore(cf, nome, cognome, numeroMaglia, idContratto);
    }

    public boolean removeMembroStaff(String cf) {
        return this.model.removeMembroStaff(cf);
    }
    public boolean registerMembroStaff(String cf, String ruolo, String nome, String cognome, int idContratto) {
        return this.model.registerMembroStaff(cf, ruolo, nome, cognome, idContratto);
    }

    public boolean removeGuida(String cf) {
        return this.model.removeGuida(cf);
    }
    public boolean registerGuida(String cf, String nome, String cognome, String turnoLavorativo, int idContratto) {
        return this.model.registerGuida(cf, nome, cognome, turnoLavorativo, idContratto);
    }

    // --- Inserimenti di supporto ---
    public boolean addContratto(int idContratto, String dataStipulazione, String durata, int stipendio) {
        return this.model.addContratto(idContratto, dataStipulazione, durata, stipendio);
    }
    public boolean addTrasferimento(int idTrasferimento, String clubCoinvolto, int valoreTrasferimento,
                                    String durataPrestitoOrNull, String dataTrasferimento, String cf) {
        return this.model.addTrasferimento(idTrasferimento, clubCoinvolto, valoreTrasferimento,
                durataPrestitoOrNull, dataTrasferimento, cf);
    }


}
