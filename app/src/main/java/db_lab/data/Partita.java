package db_lab.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Partita {

    public final int idPartita;
    public final String data;
    public final String squadraAvversaria;
    public final String competizione;
    public final String risultato;

    public Partita(int idPartita, String data, String squadraAvversaria, String competizione, String risultato) {
        this.idPartita = idPartita;
        this.data = data;
        this.squadraAvversaria = squadraAvversaria;
        this.competizione = competizione;
        this.risultato = risultato;
    }

    public String toDisplayString() {
        String vs = (squadraAvversaria == null || squadraAvversaria.isBlank()) ? "?" : squadraAvversaria;
        String comp = (competizione == null || competizione.isBlank()) ? "" : (" - " + competizione);
        return data + " vs " + vs + comp;
    }

    public static final class DAO {
        public static List<Partita> listAll(Connection connection) {
            List<Partita> list = new ArrayList<>();
            try (
                var ps = DAOUtils.prepare(connection, Queries.LIST_PARTITE);
                var rs = ps.executeQuery();
            ) {
                while (rs.next()) {
                    int id = rs.getInt("IDPartita");
                    String data = rs.getString("Data");
                    String avv = rs.getString("SquadraAvversaria");
                    String comp = rs.getString("Competizione");
                    String ris = rs.getString("Risultato");
                    list.add(new Partita(id, data, avv, comp, ris));
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return list;
        }

        public static int countSpettatori(int idPartita, Connection connection) {
            try (
                var ps = DAOUtils.prepare(connection, Queries.COUNT_SPETTATORI_PARTITA, idPartita);
                var rs = ps.executeQuery();
            ) {
                if (rs.next()) {
                    return rs.getInt("spettatori");
                }
                return 0;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }
}
    
