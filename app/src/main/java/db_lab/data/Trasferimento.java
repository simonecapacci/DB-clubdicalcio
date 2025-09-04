package db_lab.data;

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
}