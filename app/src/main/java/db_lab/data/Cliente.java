package db_lab.data;

import java.sql.Connection;
import java.sql.SQLException;

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
        this.Indirizzodispedizione = Indirizzodispedizione;
        this.Mail = Mail;
        this.Password = Password;
    }
    

    public static final class DAO{
        public static Cliente loginCliente(String Mail, String Password, Connection connection){
            Cliente cliente = null;
            if (Mail == null || Password == null){
                return null;
            }
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.FIND_CLIENT, Mail, Password);
                var resultSet = preparedStatement.executeQuery();
            ) {
                if (resultSet.next()) {
                    var Cf = resultSet.getString("CF");
                    var nome = resultSet.getString("Nome");
                    var cognome = resultSet.getString("Cognome");
                    var indirizzodispedizione = resultSet.getString("Indirizzodispedizione");
                    var email = resultSet.getString("Mail");
                    var password = resultSet.getString("Password");
                    cliente = new Cliente(Cf, nome, cognome, indirizzodispedizione, email, password);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return cliente;
        }

        public static boolean findClienteByCF(String cf, Connection connection) {
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.FIND_CLIENT_BY_CF, cf);
                var resultSet = preparedStatement.executeQuery();
            ) {
                if(resultSet.next()){
                   return true; 
                } 
                else{
                   return false;
                }
                
            } catch (SQLException e){
                return false;
            }
            
        }

        public static boolean registerCliente(String cf, String nome, String cognome, String indirizzodispedizione, String email, String pass, Connection connection) {
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SIGN_UP, cf, nome, cognome, indirizzodispedizione, email, pass);    
            ) {
                preparedStatement.executeUpdate();
                return true;
            } catch (SQLException e){
                return false;
            }
        }
    }
}
