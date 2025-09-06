package db_lab.data;

public class Biglietto {
    public final int codiceProdotto;
    public final int idPartita;
    public final int codiceOrdine;
    

    public Biglietto(int codiceOrdine, int idPartita, int codiceProdotto){
        this.codiceOrdine=codiceOrdine;
        this.idPartita = idPartita;
        this.codiceProdotto=codiceProdotto;
    }
}
