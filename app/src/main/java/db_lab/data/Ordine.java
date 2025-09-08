package db_lab.data;

public class Ordine {
    public final int codiceOrdine;
    public final int data;
    public final int rimborsato;
    public final String cf;

      public Ordine(int codiceOrdine, int data, int rimborsato, String cf){
        this.codiceOrdine=codiceOrdine;
        this.data=data;
        this.rimborsato=rimborsato;
        this.cf = cf;
      }
    
}
