package db_lab.data;

public class Prodotto {

    public final String codiceProdotto;
    public final float importo;
    public final String tipologia;
    public final String nome;




public Prodotto(String codiceProdotto, float importo, String tipologia, String nome) {
        this.codiceProdotto = codiceProdotto;
        this.importo = importo;
        this.tipologia = tipologia;
        this.nome = nome;
    }
}