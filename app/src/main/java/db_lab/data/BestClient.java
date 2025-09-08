package db_lab.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BestClient {
    public final String CF;
    public final String nome;
    public final String cognome;
    public final double totaleSpeso;

    public BestClient(String CF, String nome, String cognome, double totaleSpeso) {
        this.CF = CF;
        this.nome = nome;
        this.cognome = cognome;
        this.totaleSpeso = totaleSpeso;
    }

    public static final class DAO {
        public static List<BestClient> listTop(Connection connection) {
            List<BestClient> list = new ArrayList<>();
            try (
                var ps = DAOUtils.prepare(connection, Queries.LIST_BEST_CLIENTI);
                var rs = ps.executeQuery();
            ) {
                while (rs.next()) {
                    String cf = rs.getString("CF");
                    String nome = rs.getString("Nome");
                    String cognome = rs.getString("Cognome");
                    double totale = rs.getDouble("TotaleSpeso");
                    list.add(new BestClient(cf, nome, cognome, totale));
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return list;
        }
    }
}

