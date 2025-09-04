package db_lab.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public final class Trasferimento {

    public final int IDTrasferimento;
    public final String clubCoinvolto;
    public final int valoreTrasferimento;
    public final String dataTrasferimento;
    public String durataPrestito;
    public final String cf;



    public Trasferimento(int IDTrasferimento, String clubCoinvolto, int valoreTrasferimento,Optional<String> durataPrestito, String dataTrasferimento, String cf) {
        this.IDTrasferimento = IDTrasferimento;
        this.clubCoinvolto = clubCoinvolto;
        this.valoreTrasferimento = valoreTrasferimento;
        if (durataPrestito.isPresent()){
            this.durataPrestito = durataPrestito.get();
        }
        this.dataTrasferimento = dataTrasferimento;
        this.cf = cf;
    }

    public static final class DAO {
        public static boolean addTrasferimento(int idTrasferimento, String clubCoinvolto, int valoreTrasferimento,
                                               String durataPrestitoOrNull, String dataTrasferimento, String cf,
                                               Connection connection) {
            try (var ps = DAOUtils.prepare(connection, Queries.INSERT_TRASFERIMENTO,
                    idTrasferimento, clubCoinvolto, valoreTrasferimento, durataPrestitoOrNull, dataTrasferimento, cf)) {
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                return false;
            }
        }
    }
}
