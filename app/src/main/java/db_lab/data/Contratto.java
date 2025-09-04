package db_lab.data;

import java.sql.Connection;
import java.sql.SQLException;

public class Contratto {
    public final int idContratto;
    public final String dataStipulazione;
    public final String durata;
    public final int stipendio;

        public Contratto(int idContratto, String dataStipulazione, String  durata, int stipendio){
            this.dataStipulazione = dataStipulazione;
            this.durata = durata;
            this.stipendio = stipendio;
            this.idContratto = idContratto;
        }

        public static final class DAO {
            public static boolean addContratto(int idContratto, String dataStipulazione, String durata, int stipendio, Connection connection) {
                try (var ps = DAOUtils.prepare(connection, Queries.INSERT_CONTRATTO,
                        idContratto, dataStipulazione, durata, stipendio)) {
                    ps.executeUpdate();
                    return true;
                } catch (SQLException e) {
                    return false;
                }
            }
        }
}
