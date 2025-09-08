package db_lab.data;

import java.sql.Connection;
import java.sql.SQLException;

public class MatchRevenue {
    public final int idPartita;
    public final String data;
    public final String squadraAvversaria;
    public final String competizione;
    public final String risultato;
    public final int bigliettiVenduti;
    public final double totaleIncasso;

    public MatchRevenue(int idPartita, String data, String squadraAvversaria,
                        String competizione, String risultato,
                        int bigliettiVenduti, double totaleIncasso) {
        this.idPartita = idPartita;
        this.data = data;
        this.squadraAvversaria = squadraAvversaria;
        this.competizione = competizione;
        this.risultato = risultato;
        this.bigliettiVenduti = bigliettiVenduti;
        this.totaleIncasso = totaleIncasso;
    }

    public static final class DAO {
        public static MatchRevenue getMostProfitable(Connection connection) {
            try (
                var ps = DAOUtils.prepare(connection, Queries.MOST_PROFITABLE_MATCH);
                var rs = ps.executeQuery();
            ) {
                if (rs.next()) {
                    int id = rs.getInt("IDPartita");
                    String data = rs.getString("Data");
                    String avv = rs.getString("SquadraAvversaria");
                    String comp = rs.getString("Competizione");
                    String ris = rs.getString("Risultato");
                    int qty = rs.getInt("BigliettiVenduti");
                    double inc = rs.getDouble("TotaleIncasso");
                    return new MatchRevenue(id, data, avv, comp, ris, qty, inc);
                }
                return null;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }
}

