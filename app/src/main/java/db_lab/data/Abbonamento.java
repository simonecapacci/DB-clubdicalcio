package db_lab.data;

public class Abbonamento {
    public final int idAbbonamento;
    public final int stagione;
    public final String tipoAbbonamento;
    
        public Abbonamento(int idAbbonamento, int stagione, String tipoAbbonamento){
            this.idAbbonamento=idAbbonamento;
            this.stagione=stagione;
            this.tipoAbbonamento=tipoAbbonamento;
        }
}
