package db_lab.data;

public class Articolo_personale {
    public final String tipoArticolo;
    public final int codiceProdotto;
    public final int importo;

    public Articolo_personale(String tipoArticolo, int codiceProdotto, int importo){
        this.tipoArticolo=tipoArticolo;
         this.importo=importo;
        this.codiceProdotto=codiceProdotto;
    }
}
