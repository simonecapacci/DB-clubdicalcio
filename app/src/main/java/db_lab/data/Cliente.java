package db_lab.data;

import java.sql.Connection;

public class Cliente {
    public final String CF;
    public final String Nome;
    public final String Cognome;
    public final String Indirizzodispedizione;
    public final String Mail;
    public final String Password;

    public Cliente(String CF, String Nome, String Cognome, String Indirizzodispedizione, String Mail, String Password){
        this.CF = CF;
        this.Nome = Nome;
        this.Cognome = Cognome;
        this. Indirizzodispedizione = Indirizzodispedizione;
        this.Mail = Mail;
        this.Password = Password;
    }

    public static final class DAO{
        public static Cliente loginCliente(String Mail, String Password, Connection connection){
            Cliente cliente = null;
            if (Mail==null || Password == null ){
                return null;
            }
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.FIND_CLIENTE/*da cambiare */, Mail, Password);
                var resultSet = preparedStatement.executeQuery();
            ) {
                if (resultSet.next()) {
                    var Cf = resultSet.getString("CF");
                    var nome = resultSet.getString("Nome");
                    var cognome = resultSet.getString("Cognome");
                    var email = resultSet.getString("E_mail");
                    var password = resultSet.getString("Password");
                    cliente = new Cliente(Cf, nome, cognome, cognome, email, password);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return cliente;
        }
    }
}
