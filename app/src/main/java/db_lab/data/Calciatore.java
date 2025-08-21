package db_lab.data;

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
}
