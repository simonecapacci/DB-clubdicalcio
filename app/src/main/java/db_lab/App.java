package db_lab;

import db_lab.controller.Controller;
import db_lab.data.DAOException;
import db_lab.data.DAOUtils;
import db_lab.model.Model;
import db_lab.view.JuventusMenu;

import javax.swing.SwingUtilities;
import java.sql.SQLException;

public final class App {

    public static void main(String[] args) throws SQLException {
        var connection = DAOUtils.localMySQLConnection("juventusfc", "root", "NuovaPassword123!");
        var model = Model.fromConnection(connection);
        var view = new JuventusMenu(() -> {
            // We want to make sure we close the connection when we're done
            // with our application.
            try {
                connection.close();
            } catch (Exception ignored) {}
        });
        view.setUp();
        var controller = new Controller(model);
        view.setController(controller);
        //controller.userRequestedInitialPage();
    }
}
