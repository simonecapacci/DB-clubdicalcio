package db_lab.data;

public final class VisitaGuidata {
    public final int codiceProdotto;
    public final String data;
    public final String cfGuida;
    public final int codiceOrdine;


public VisitaGuidata(int codiceProdotto, String data, String cfGuida, int codiceOrdine ) {
        this.codiceProdotto = codiceProdotto;
        this.data = data;
        this.cfGuida = cfGuida;
        this.codiceOrdine = codiceOrdine;
    }
}