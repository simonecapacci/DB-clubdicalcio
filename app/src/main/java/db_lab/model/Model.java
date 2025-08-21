package db_lab.model;

import db_lab.data.Product;
import db_lab.data.ProductPreview;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface Model {

    // Create a model that connects to a database using the given connection.
    //
    static Model fromConnection(Connection connection) {
        return new DBModel(connection);
    }

    void loginCliente(String email, String pass);
}
