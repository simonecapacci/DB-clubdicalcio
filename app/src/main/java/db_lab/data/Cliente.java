package db_lab.data;

public class Cliente {
 public final String indirizzoSpedizione;
 public final String mail;
 public final String Password;
 public final String CF;
 public final String nome;
public final String cognome;

      public Cliente(String indirizzoSpedizione, String mail, String password, String CF, String nome, String cognome){
        this.indirizzoSpedizione=indirizzoSpedizione;
        this.mail=mail;
        this.Password=password;
        this.cognome=cognome;
        this.nome=nome;
        this.CF=CF;
      }
}
