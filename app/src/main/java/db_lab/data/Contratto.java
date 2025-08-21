package db_lab.data;

public class Contratto {
<<<<<<< HEAD
    public final int codiceContratto;
=======
    public final int idContratto;
>>>>>>> 4f4d97f803f07ab1d33ce6c0574fb025f4c99b20
    public final int dataStipulazione;
    public final int durata;
    public final int stipendio;

<<<<<<< HEAD
     public Contratto(int durata, int codiceContratto, int dataStipulazione, int stipendio){
        this.durata=durata;
        this.codiceContratto=codiceContratto;
        this.dataStipulazione=dataStipulazione;
        this.stipendio=stipendio;

     }
=======
        public Contratto(int idContratto, int dataStipulazione, int durata, int stipendio){
            this.dataStipulazione=dataStipulazione;
            this.durata=durata;
            this.stipendio=stipendio;
            this.idContratto=idContratto;
        }
>>>>>>> 4f4d97f803f07ab1d33ce6c0574fb025f4c99b20
}
