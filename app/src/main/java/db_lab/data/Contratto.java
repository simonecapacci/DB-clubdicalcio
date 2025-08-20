package db_lab.data;

public class Contratto {
    public final int codiceContratto;
    public final int dataStipulazione;
    public final int durata;
    public final int stipendio;

     public Contratto(int durata, int codiceContratto, int dataStipulazione, int stipendio){
        this.durata=durata;
        this.codiceContratto=codiceContratto;
        this.dataStipulazione=dataStipulazione;
        this.stipendio=stipendio;

     }
}
