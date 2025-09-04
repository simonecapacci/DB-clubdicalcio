package db_lab.data;

import java.sql.Connection;
import java.sql.SQLException;

public class Guida {

public final String CF;
 public final String nome;
public final String cognome;
public final int turnoLavorativo;
    public Guida(String CF, String nome, String cognome, int turnoLavorativo ){
        this.cognome=cognome;
        this.nome=nome;
        this.CF=CF;
        this.turnoLavorativo=turnoLavorativo;
    } 

    public static final class DAO {
        public static boolean removeGuida(String cf, Connection connection) {
            if (cf == null || cf.isBlank()) return false;
            try (var ps = DAOUtils.prepare(connection, Queries.REMOVE_GUIDA, cf)) {
                int updated = ps.executeUpdate();
                return updated > 0;
            } catch (SQLException e) {
                return false;
            }
        }

        public static boolean registerGuida(String cf, String nome, String cognome, int turnoLavorativo, int idContratto, Connection connection) {
            if (cf == null || cf.isBlank()) return false;
            try (var ps = DAOUtils.prepare(connection, Queries.INSERT_GUIDA, cf, nome, cognome, turnoLavorativo, idContratto)) {
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                return false;
            }
        }
    }
}
