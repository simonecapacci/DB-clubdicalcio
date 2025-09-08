package db_lab.data;

public class OrderItem {
    public final String nome;
    public final String tipologia;
    public final double importo;

    public OrderItem(String nome, String tipologia, double importo) {
        this.nome = nome;
        this.tipologia = tipologia;
        this.importo = importo;
    }
}

