package db_lab.data;

public class Dirigente {
<<<<<<< HEAD
    public final String ruolo;
    public final String nome;
    public final String cognome;
    public final String codicefiscale;

    public Dirigente(String nome, String cognome, String ruolo, String codicefiscale){
        this.nome=nome;
        this.cognome=cognome;
        this.codicefiscale=codicefiscale;
=======
public final String CF;
 public final String nome;
public final String cognome;
public final String ruolo;
    public Dirigente(String CF, String nome, String cognome, String ruolo ){
         this.cognome=cognome;
        this.nome=nome;
        this.CF=CF;
>>>>>>> 4f4d97f803f07ab1d33ce6c0574fb025f4c99b20
        this.ruolo=ruolo;
    }
}
