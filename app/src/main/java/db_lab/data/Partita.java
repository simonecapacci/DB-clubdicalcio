package db_lab.data;

public class Partita {
       
       public final String data;
       public final String squadraAvversaria;
       public final String competizione;
       public final int risultato;

}

public Partita (String data, String squadraAvversaria, String competizione, int risultato) {
        this.data = data;
        this.squadraAvversaria = squadraAvversaria;
        this.competizione = competizione;
        this.risultato = risultato;
    }

    