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

        public static boolean findCalciatoreByCF(String cf, Connection connection) {
            if (cf == null || cf.isBlank()) return false;
            try (
                var ps = DAOUtils.prepare(connection, Queries.FIND_CALCIATORE_BY_CF, cf);
                var rs = ps.executeQuery();
            ) {
                return rs.next();
            } catch (SQLException e) {
                return false;
            }
        }

        public static boolean removeCalciatore(String cf, Connection connection) {
            if (cf == null || cf.isBlank()) return false;
            try (
                var ps = DAOUtils.prepare(connection, Queries.REMOVE_CALCIATORE, cf);
            ) {
                int updated = ps.executeUpdate();
                return updated > 0;
            } catch (SQLException e) {
                return false;
            }
        }

        public static boolean registerCalciatore(String cf, String nome, String cognome, int numeroMaglia, int idContratto, Connection connection) {
            if (cf == null || cf.isBlank()) return false;
            try (
                var ps = DAOUtils.prepare(connection, Queries.INSERT_CALCIATORE, cf, nome, cognome, numeroMaglia, idContratto);
            ) {
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                return false;
            }
        }

        public static TopSeller getTopJerseySeller(Connection connection) {
            String sql = "SELECT c.Nome, c.Cognome, COUNT(*) AS Vendite " +
                         "FROM articolo_personale ap " +
                         "JOIN calciatore c ON c.CF = ap.CFCalciatore " +
                         "GROUP BY c.CF, c.Nome, c.Cognome " +
                         "ORDER BY Vendite DESC " +
                         "LIMIT 1";
            try (
                var ps = connection.prepareStatement(sql);
                var rs = ps.executeQuery();
            ) {
                if (rs.next()) {
                    String nome = rs.getString("Nome");
                    String cognome = rs.getString("Cognome");
                    int vendite = rs.getInt("Vendite");
                    return new TopSeller(nome, cognome, vendite);
                }
                return null;
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

        public static java.util.List<Calciatore> listAll(Connection connection) {
            java.util.List<Calciatore> list = new java.util.ArrayList<>();
            try (
                var ps = DAOUtils.prepare(connection, Queries.LIST_CALCIATORI);
                var rs = ps.executeQuery();
            ) {
                while (rs.next()) {
                    String cf = rs.getString("CF");
                    String nome = rs.getString("Nome");
                    String cognome = rs.getString("Cognome");
                    int numero = rs.getInt("NumeroDiMaglia");
                    // 'posizione' non presente: set a 0
                    list.add(new Calciatore(0, numero, nome, cognome, cf));
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return list;
        }

    }
}
