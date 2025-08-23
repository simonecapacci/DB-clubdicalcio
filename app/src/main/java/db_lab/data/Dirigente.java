package db_lab.data;

import java.sql.Connection;

public class Dirigente {
public final String CF;
public final String nome;
public final String cognome;
public final String ruolo;
public final int IDcontratto;
public final String password;
    public Dirigente(String CF, String ruolo, String cognome, String nome, int IDcontratto, String password){
        this.cognome=cognome;
        this.nome=nome;
        this.CF=CF;
        this.ruolo=ruolo;
        this.IDcontratto = IDcontratto;
        this.password=password;
    }
    public static class DAO{
        public static Dirigente loginAdmin(String cf, String pass, Connection connection){
            Dirigente admin = null;
            if (cf==null || pass == null){
                return null;
            }
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.FIND_ADMIN, cf, pass);
                var resultSet = preparedStatement.executeQuery();
            ) {
                if (resultSet.next()) {
                    var Cf = resultSet.getString("CF");
                    var nome = resultSet.getString("Nome");
                    var cognome = resultSet.getString("Cognome");
                    var ruolo = resultSet.getString("Ruolo");
                    var IDcontratto = resultSet.getInt("IDcontratto");
                    var password = resultSet.getString("Password");
                    admin = new Dirigente(Cf, ruolo, cognome, nome, IDcontratto, password);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return admin;
        }
    }
}
