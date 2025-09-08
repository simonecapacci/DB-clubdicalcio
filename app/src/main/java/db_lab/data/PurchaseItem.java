package db_lab.data;

public class PurchaseItem {
    public final int codiceProdotto;
    public final String tipologia;
    public final String cfCalciatore; // nullable
    public final String dataVisita;   // nullable, ISO yyyy-MM-dd
    public final String slotVisita;   // nullable, e.g. "mattina" | "pomeriggio"

    public PurchaseItem(int codiceProdotto, String tipologia, String cfCalciatore, String dataVisita, String slotVisita) {
        this.codiceProdotto = codiceProdotto;
        this.tipologia = tipologia;
        this.cfCalciatore = cfCalciatore;
        this.dataVisita = dataVisita;
        this.slotVisita = slotVisita;
    }
}
