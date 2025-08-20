package db_lab.data;

public class Contratto {
    public final int idContratto;
    public final int dataStipulazione;
    public final int durata;
    public final int stipendio;

        public Contratto(int idContratto, int dataStipulazione, int durata, int stipendio){
            this.dataStipulazione=dataStipulazione;
            this.durata=durata;
            this.stipendio=stipendio;
            this.idContratto=idContratto;
        }
}
