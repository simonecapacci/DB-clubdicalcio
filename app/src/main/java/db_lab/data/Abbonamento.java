package db_lab.data;

import java.sql.Connection;
import java.sql.SQLException;

public class Abbonamento {
    public final int idAbbonamento;
    public final int stagione;
    public final String tipoAbbonamento;
    public final String cf;
    
    public Abbonamento(int idAbbonamento, int stagione, String tipoAbbonamento, String cf){
        this.idAbbonamento = idAbbonamento;
        this.stagione = stagione;
        this.tipoAbbonamento = tipoAbbonamento;
        this.cf = cf;
    }

    public static final class DAO {
        public static boolean addAbbonamento(String cf, String tipoAbbonamento, int anno, Connection connection) {
            if (cf == null || cf.isBlank() || tipoAbbonamento == null || tipoAbbonamento.isBlank()) {
                return false;
            }
            try (
                var psMax = DAOUtils.prepare(connection, Queries.MAX_ABBONAMENTO_ID);
            ) {
                int nextId = 1;
                try (var rs = psMax.executeQuery()) {
                    if (rs.next()) {
                        nextId = rs.getInt("maxId") + 1;
                    }
                }

                try (var psIns = DAOUtils.prepare(connection, Queries.INSERT_ABBONAMENTO, nextId, anno, tipoAbbonamento, cf)) {
                    psIns.executeUpdate();
                    return true;
                }
            } catch (SQLException e) {
                return false;
            }
        }
    }
}
