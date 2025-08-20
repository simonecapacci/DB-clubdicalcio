package db_lab.controller;

import db_lab.view.JuventusMenu;

public class MainController {
    private final JuventusMenu menu;

    public MainController() {
        this.menu = new JuventusMenu(this::onClose, this);
    }

    private void onClose(){ /* eventuale cleanup */ }

    // Factory verso sottocontroller (invocati dal menu)
    public AuthController auth(){ return new AuthController(); }
    public SubscriptionController subs(){ return new SubscriptionController(); }
    public ShopController shop(){ return new ShopController(); }
    public MatchesController matches(){ return new MatchesController(); }
    public AdminController admin(){ return new AdminController(); }
}
