package db_lab.data;

public final class Queries {

    // --- Admin: gestione dipendenti ---
    public static final String REMOVE_CALCIATORE =
        """
        DELETE FROM CALCIATORE
        WHERE CF = ?
        """;

    public static final String INSERT_CALCIATORE =
        """
        INSERT INTO CALCIATORE (CF, NOME, COGNOME, NUMEROMAGLIA, IDCONTRATTO)
        VALUES (?, ?, ?, ?, ?)
        """;

    public static final String FIND_CALCIATORE_BY_CF =
        """
        SELECT 1
        FROM CALCIATORE
        WHERE CF = ?
        LIMIT 1
        """;

    public static final String REMOVE_MEMBROSTAFF =
        """
        DELETE FROM MEMBROSTAFF
        WHERE CF = ?
        """;

    public static final String INSERT_MEMBROSTAFF =
        """
        INSERT INTO MEMBROSTAFF (CF, RUOLO, NOME, COGNOME, IDCONTRATTO)
        VALUES (?, ?, ?, ?, ?)
        """;

    public static final String REMOVE_GUIDA =
        """
        DELETE FROM GUIDA
        WHERE CF = ?
        """;

    public static final String INSERT_GUIDA =
        """
        INSERT INTO GUIDA (CF, NOME, COGNOME, TURNOLAVORATIVO, IDCONTRATTO)
        VALUES (?, ?, ?, ?, ?)
        """;

    public static final String INSERT_CONTRATTO =
        """
        INSERT INTO CONTRATTO (IDCONTRATTO, DATASTIPULAZIONE, DURATA, STIPENDIO)
        VALUES (?, ?, ?, ?)
        """;

    public static final String INSERT_TRASFERIMENTO =
        """
        INSERT INTO TRASFERIMENTO (IDTRASFERIMENTO, CLUBCOINVOLTO, VALORETRASFERIMENTO, DURATAPRESTITO, DATATRASFERIMENTO, CF)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

    public static final String VIEW_CLIENTS_AT_A_GAME =
        """
        SELECT Pr.anno, Pr.data, COUNT(*) as tifosi 

        FROM Cliente as c , Presenze as Pr 

        WHERE Pr.anno= ”?” 

        AND Pr.data= “?” 

        GROUP BY Cliente

        """;

    public static final String VIEW_PLAYER_WITH_MOST_SELLS =
        """
        SELECT C.Nome , C.Cognome,COUNT(*) 

        FROM Articolo Personale as AP, Calciatore as C 

        WHERE C.CF=AP.CF 

        AND  AP.Tipo di articolo = ‘Maglietta’ 

        GROUP BY C.Nome,C.Cognome 

        ORDER BY COUNT(*) DESC

        LIMIT 1
        """;

    public static final String BEST_PLAYERS_BY_GOALS =
        """
        SELECT c.Nome, c.Cognome , SUM(GOL)

        FROM Calciatore as c,  statistiche_individuali as si 

        WHERE c.CF = si.CF	 

        GROUP BY c.Nome,c.Cognome
        
        HAVING SUM(GOL) > 0 

        ORDER BY SUM(GOL) DESC 
 
        """;
    public static final String BEST_PLAYERS_BY_ASSISTS = 
        """
        SELECT c.Nome, c.Cognome , SUM(ASSIST) 

        FROM Calciatore as c,  statistiche_individuali as si 

        WHERE c.CF = si.CF	 

        GROUP BY c.Nome,c.Cognome
        
        HAVING SUM(ASSIST) > 0 

        ORDER BY SUM(ASSIST) DESC
        
        """;

    public static final String  ORDER_SUM =
        """
        (SELECT o.codiceordine, SUM(p.importo) as importoordine 

        FROM Prodotto as p, Ordine as o 

        WHERE o.codiceordine=p.codiceordine 

        AND o.rimborsato=false 

        GROUP BY o.codiceordine) as SO 

        """;

    public static final String  IS_SUBSCRIBED =
        """
        (SELECT  SO.*,  

        CASE  

        WHEN s.codiceordine IS NOT NULL  

        THEN SO.importoordine * (1 - s.scontopercentuale) 

        ELSE SO.importoordine  

        END AS PrezzoScontato 

        FROM SO 

        LEFT JOIN Sconti s ON SO.codiceordine = s.codiceordine) as PS 

        """;

    public static final String  SUM_ORDERS_SINGLE_CLIENT =
        """
        SELECT CF,Nome, Cognome, SUM(PS.PrezzoScontato)  

        FROM cliente as c, PS 

        GROUP BY CF, Nome, Cognome 

        ORDER BY SUM(PS.PrezzoScontato) DESC 

        LIMIT 30  

        """;
    
    public static final String  SUM_PRICE_TICKETS =
        """
        (SELECT o.codiceordine, p.data, p.anno, SUM(b.importo) as biglietto ordinato 

        FROM Biglietto as b, Ordine as o, Partita as p 

        WHERE o.codiceordine=b.codiceordine 

        AND p.data=b.data 

        AND p.anno=b.anno 

        AND o.rimborsato=false 

        GROUP BY o.codiceordine, p.data, p.anno) as SO

        """;

    public static final String  IS_FROM_A_SUBSCRIBER =
        """
        (SELECT  SO.*,  

        CASE  

        WHEN s.codiceordine IS NOT NULL  

        THEN SO.importobigliettordinato * (1 - s.scontopercentuale) 

        ELSE SO.importobigliettordinato  
        
        END AS PrezzoScontato 

        FROM SO 

        LEFT JOIN Sconti s ON SO.codiceordine = s.codiceordine) as PS 

        """;
    
    public static final String  TICKETS_SINGLE_MATCH_SUM =
        """
        SELECT p.* , SUM(PS.PrezzoScontato)  

        FROM PS, Partita as p 

        GROUP BY PS.data, PS.anno 

        ORDER BY SUM(PS.PrezzoScontato) DESC 

        LIMIT 1 

        """;
    public static final String FIND_CLIENT = 
        """
        SELECT *
        FROM CLIENTE
        WHERE CLIENTE.MAIL = ?
        AND CLIENTE.PASSWORD = ? 
               
        """;
    public static final String FIND_ADMIN = 
        """
        SELECT *
        FROM DIRIGENTE
        WHERE DIRIGENTE.CF = ?
        AND DIRIGENTE.PASS = ?
        """;
    public static final String FIND_CLIENT_BY_CF = 
        """
        SELECT *
        FROM CLIENTE
        WHERE CLIENTE.CF = ?

        """;
    public static final String SIGN_UP = 
        """
        INSERT INTO CLIENTE
        (CF, NOME, COGNOME, INDIRIZZODISPEDIZIONE, MAIL, PASSWORD) 
        VALUES
        (?, ?, ?, ?, ?, ?)       
        """;

    // --- Partite & Presenze ---
    public static final String LIST_PARTITE =
        """
        SELECT IDPartita, Data, SquadraAvversaria, Competizione, Risultato
        FROM partita
        ORDER BY Data DESC, IDPartita DESC
        """;

    public static final String COUNT_SPETTATORI_PARTITA =
        """
        SELECT COUNT(*) AS spettatori
        FROM presenze
        WHERE IDPartita = ?
        """;

    // --- Abbonamento ---
    public static final String MAX_ABBONAMENTO_ID =
        """
        SELECT COALESCE(MAX(IDabbonamento), 0) AS maxId
        FROM abbonamento
        """;

    public static final String INSERT_ABBONAMENTO =
        """
        INSERT INTO abbonamento (IDabbonamento, Anno, Tipodiabbonamento, CF)
        VALUES (?, ?, ?, ?)
        """;
}
