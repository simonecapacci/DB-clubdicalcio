package db_lab.model;

import db_lab.data.AssistPersonali;
import db_lab.data.Calciatore;
import db_lab.data.Cliente;
import db_lab.data.GoalPersonali;

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
}
