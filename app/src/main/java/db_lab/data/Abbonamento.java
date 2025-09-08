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
        public static boolean hasAbbonamento(String cf, int anno, Connection connection) {
            if (cf == null || cf.isBlank()) return false;
            try (
                var ps = DAOUtils.prepare(connection, Queries.EXISTS_ABBONAMENTO_PER_ANNO, cf, anno);
                var rs = ps.executeQuery();
            ) {
                return rs.next();
            } catch (SQLException e) {
                return false;
            }
        }

        public static boolean addAbbonamento(String cf, String tipoAbbonamento, int anno, Connection connection) {
            if (cf == null || cf.isBlank() || tipoAbbonamento == null || tipoAbbonamento.isBlank()) {
                return false;
            }
            try (
                var psCheck = DAOUtils.prepare(connection, Queries.EXISTS_ABBONAMENTO_PER_ANNO, cf, anno);
                var psMax = DAOUtils.prepare(connection, Queries.MAX_ABBONAMENTO_ID);
            ) {
                try (var rsC = psCheck.executeQuery()) {
                    if (rsC.next()) {
                        // already subscribed for this year
                        return false;
                    }
                }
                int nextId = 1;
                try (var rs = psMax.executeQuery()) {
                    if (rs.next()) {
                        nextId = rs.getInt("maxId") + 1;
                    }
                }

                float sconto = computeScontoPercentuale(tipoAbbonamento);
                try (var psIns = DAOUtils.prepare(connection, Queries.INSERT_ABBONAMENTO, nextId, anno, tipoAbbonamento, cf, sconto)) {
                    psIns.executeUpdate();
                    return true;
                }
            } catch (SQLException e) {
                return false;
            }
        }

        public static float getScontoPercentuale(String cf, int anno, Connection connection) {
            if (cf == null || cf.isBlank()) return 0f;
            try (
                var ps = DAOUtils.prepare(connection, Queries.GET_SCONTO_ABBONAMENTO, cf, anno);
                var rs = ps.executeQuery();
            ) {
                if (rs.next()) {
                    return rs.getFloat("Sconto");
                }
                return 0f;
            } catch (SQLException e) {
                return 0f;
            }
        }

        private static float computeScontoPercentuale(String tipo) {
            String t = tipo == null ? "" : tipo.trim().toLowerCase();
            return switch (t) {
                case "completo" -> 20f;
                case "normale" -> 15f;
                case "essenziale" -> 10f;
                default -> 0f;
            };
        }
    }
}
