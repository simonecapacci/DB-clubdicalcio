package db_lab.data;

public class Calciatorevenduto {
<<<<<<< HEAD
   
    public final int posizione;

    public Calciatorevenduto(int posizione){
        this.posizione=posizione;
    }


=======
      public final int posizione;
    public final String nome;
    public final String cognome;
    public final String CF;

    public Calciatorevenduto(int posizione, String nome, String cognome, String CF ){
        this.posizione=posizione;
        this.cognome=cognome;
        this.nome=nome;
        this.CF=CF;
    }
>>>>>>> 4f4d97f803f07ab1d33ce6c0574fb025f4c99b20
}
