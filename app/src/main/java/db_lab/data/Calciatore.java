package db_lab.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Calciatore {
   
    public final int numeroMaglia;
    public final int posizione;
    public final String nome;
    public final String cognome;
    public final String CF;

    public Calciatore(int posizione, int numeroMaglia, String nome, String cognome, String CF ){
        this.posizione=posizione;
        this.cognome=cognome;
        this.numeroMaglia=numeroMaglia;
        this.nome=nome;
        this.CF=CF;
    }
    public static final class DAO{

        public static List<AssistPersonali> getTopAssistmen(Connection connection) {
            List<AssistPersonali> list = new ArrayList<>();
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.BEST_PLAYERS_BY_ASSISTS);
                var resultList = preparedStatement.executeQuery();
            ) {
                while (resultList.next()){
                    var nome = resultList.getString("Nome");
                    var cognome = resultList.getString("Cognome");
                    var assists = resultList.getInt("SUM(ASSIST)");
                    list.add(new AssistPersonali(nome,cognome,assists));
                }
                
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return list;
        }

        public static List<GoalPersonali> getTopScorers(Connection connection) {
            List<GoalPersonali> list = new ArrayList<>();
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.BEST_PLAYERS_BY_GOALS);
                var resultList = preparedStatement.executeQuery();
            ) {
                while (resultList.next()){
                    var nome = resultList.getString("Nome");
                    var cognome = resultList.getString("Cognome");
                    var assists = resultList.getInt("SUM(GOL)");
                    list.add(new GoalPersonali(nome,cognome,assists));
                }
                
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return list;
        }

    }
}