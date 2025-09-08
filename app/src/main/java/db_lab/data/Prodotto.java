package db_lab.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Prodotto {

    public final String codiceProdotto;
    public final float importo;
    public final String tipologia;
    public final String nome;

    public Prodotto(String codiceProdotto, float importo, String tipologia, String nome) {
        this.codiceProdotto = codiceProdotto;
        this.importo = importo;
        this.tipologia = tipologia;
        this.nome = nome;
    }

    public static final class DAO {
        public static List<Prodotto> listAll(Connection connection) {
            List<Prodotto> list = new ArrayList<>();
            try (
                var ps = DAOUtils.prepare(connection, Queries.LIST_PRODOTTI);
                var rs = ps.executeQuery();
            ) {
                while (rs.next()) {
                    String codice = String.valueOf(rs.getInt("Codiceprodotto"));
                    float importo = rs.getFloat("Importo");
                    String nome = rs.getString("Nome");
                    String tip = rs.getString("Tipologia");
                    list.add(new Prodotto(codice, importo, tip, nome));
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return list;
        }
    }
}
