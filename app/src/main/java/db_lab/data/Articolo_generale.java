package db_lab.data;

public class Articolo_generale {
<<<<<<< HEAD
        String TipodiArticolo;

        public Articolo_generale(String TipodiArticolo){
            this.TipodiArticolo=TipodiArticolo;
        }
=======
    public final String tipoArticolo;
    public final int codiceProdotto;
    public final int importo;
    public Articolo_generale(String tipoArticolo, int codiceProdotto, int importo){
        this.tipoArticolo=tipoArticolo;
         this.importo=importo;
        this.codiceProdotto=codiceProdotto;
    }
>>>>>>> 4f4d97f803f07ab1d33ce6c0574fb025f4c99b20
}
