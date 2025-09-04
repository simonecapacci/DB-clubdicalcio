package db_lab.data;

public class Contratto {
    public final int idContratto;
    public final String dataStipulazione;
    public final String durata;
    public final int stipendio;

        public Contratto(int idContratto, String dataStipulazione, String  durata, int stipendio){
            this.dataStipulazione = dataStipulazione;
            this.durata = durata;
            this.stipendio = stipendio;
            this.idContratto = idContratto;
        }
}
