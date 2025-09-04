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


}
