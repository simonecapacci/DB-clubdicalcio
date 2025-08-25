package db_lab.data;

public final class Trasferimento {

    public final String codiceTrasferimento;
    public final String clubCoinvolto;
    public final int valoreTrasferimento;
    public final String dataTrasferimento;

}

public Trasferimento(String codiceTrasferimento, String clubCoinvolto, int valoreTrasferimento, String dataTrasferimento) {
        this.codiceTrasferimento = codiceTrasferimento;
        this.clubCoinvolto = clubCoinvolto;
        this.valoreTrasferimento = valoreTrasferimento;
        this.dataTrasferimento = dataTrasferimento;
    }