package db_lab.data;

public class OrderInfo {
    public final int codiceOrdine;
    public final String data; // ISO yyyy-MM-dd

    public OrderInfo(int codiceOrdine, String data) {
        this.codiceOrdine = codiceOrdine;
        this.data = data;
    }
}

