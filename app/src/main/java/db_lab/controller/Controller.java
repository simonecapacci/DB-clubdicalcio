package db_lab.controller;

import db_lab.view.JuventusMenu;

import java.util.Objects;

import db_lab.data.Cliente;
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



}
