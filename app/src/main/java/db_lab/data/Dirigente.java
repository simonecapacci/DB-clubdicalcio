package db_lab.data;

public class Dirigente {
    public final String ruolo;
    public final String nome;
    public final String cognome;
    public final String codicefiscale;

    public Dirigente(String nome, String cognome, String ruolo, String codicefiscale){
        this.nome=nome;
        this.cognome=cognome;
        this.codicefiscale=codicefiscale;
        this.ruolo=ruolo;
    }
}
