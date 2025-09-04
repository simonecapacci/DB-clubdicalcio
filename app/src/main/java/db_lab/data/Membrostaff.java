package db_lab.data;

import java.sql.Connection;
import java.sql.SQLException;

public class Membrostaff {
    public final String CF;
    public final String nome;
    public final String cognome;
    public final String ruolo;
    
    public Membrostaff(String CF, String nome, String cognome, String ruolo ){
        this.cognome=cognome;
        this.nome=nome;
        this.CF=CF;
        this.ruolo=ruolo;
    } 

    public static final class DAO {
        public static boolean removeMembroStaff(String cf, Connection connection) {
            if (cf == null || cf.isBlank()) return false;
            try (var ps = DAOUtils.prepare(connection, Queries.REMOVE_MEMBROSTAFF, cf)) {
                int updated = ps.executeUpdate();
                return updated > 0;
            } catch (SQLException e) {
                return false;
            }
        }

        public static boolean registerMembroStaff(String cf, String ruolo, String nome, String cognome, int idContratto, Connection connection) {
            if (cf == null || cf.isBlank()) return false;
            try (var ps = DAOUtils.prepare(connection, Queries.INSERT_MEMBROSTAFF, cf, ruolo, nome, cognome, idContratto)) {
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                return false;
            }
        }
    }
}
