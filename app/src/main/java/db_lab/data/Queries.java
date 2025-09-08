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
        INSERT INTO abbonamento (IDabbonamento, Anno, Tipodiabbonamento, CF, Sconto)
        VALUES (?, ?, ?, ?, ?)
        """;

    public static final String EXISTS_ABBONAMENTO_PER_ANNO =
        """
        SELECT 1
        FROM abbonamento
        WHERE CF = ? AND Anno = ?
        LIMIT 1
        """;

    public static final String GET_SCONTO_ABBONAMENTO =
        """
        SELECT Sconto
        FROM abbonamento
        WHERE CF = ? AND Anno = ?
        LIMIT 1
        """;

    // --- Prodotti ---
    public static final String LIST_PRODOTTI =
        """
        SELECT Codiceprodotto, Importo, Nome, Tipologia
        FROM prodotto
        ORDER BY CASE Tipologia
                   WHEN 'Articologenerale' THEN 1
                   WHEN 'Articolopersonale' THEN 2
                   WHEN 'Biglietto' THEN 3
                   WHEN 'Visitaguidata' THEN 4
                   ELSE 5
                 END,
                 Nome,
                 Codiceprodotto
        """;

    // --- Calciatori ---
    public static final String LIST_CALCIATORI =
        """
        SELECT CF, Nome, Cognome, NumeroDiMaglia
        FROM calciatore
        ORDER BY Nome, Cognome
        """;

    // --- Clienti: migliori clienti per totale speso ---
    public static final String LIST_BEST_CLIENTI =
        """
        SELECT c.CF, c.Nome, c.Cognome, SUM(p.Importo) AS TotaleSpeso
        FROM ordine o
        JOIN cliente c ON c.CF = o.CF
        JOIN (
            SELECT Codiceordine, Codiceprodotto FROM biglietto
            UNION ALL
            SELECT Codiceordine, Codiceprodotto FROM articolo_personale
            UNION ALL
            SELECT Codiceordine, Codiceprodotto FROM articolo_generale
            UNION ALL
            SELECT Codiceordine, Codiceprodotto FROM visita_guidata
        ) x ON x.Codiceordine = o.Codiceordine
        JOIN prodotto p ON p.Codiceprodotto = x.Codiceprodotto
        WHERE o.Rimborsato = 0
        GROUP BY c.CF, c.Nome, c.Cognome
        ORDER BY TotaleSpeso DESC, c.Nome, c.Cognome
        LIMIT 30
        """;

    // --- Partite: partita piu' redditizia (solo biglietti) ---
    public static final String MOST_PROFITABLE_MATCH =
        """
        SELECT pt.IDPartita, pt.Data, pt.SquadraAvversaria, pt.Competizione, pt.Risultato,
               COUNT(*) AS BigliettiVenduti,
               SUM(p.Importo * (1 - (COALESCE(a.Sconto,
                                            CASE a.Tipo
                                                WHEN 'completo' THEN 20
                                                WHEN 'normale'  THEN 15
                                                WHEN 'essenziale' THEN 10
                                                ELSE 0
                                            END) / 100.0))) AS TotaleIncasso
        FROM biglietto b
        JOIN ordine o ON o.Codiceordine = b.Codiceordine
        JOIN prodotto p ON p.Codiceprodotto = b.Codiceprodotto
        JOIN partita pt ON pt.IDPartita = b.IDPartita
        LEFT JOIN (
            SELECT CF, Anno, MAX(Sconto) AS Sconto, MAX(Tipodiabbonamento) AS Tipo
            FROM abbonamento
            GROUP BY CF, Anno
        ) a ON a.CF = o.CF AND a.Anno = CAST(SUBSTRING(o.Data, 1, 4) AS UNSIGNED)
        WHERE o.Rimborsato = 0
        GROUP BY pt.IDPartita, pt.Data, pt.SquadraAvversaria, pt.Competizione, pt.Risultato
        ORDER BY TotaleIncasso DESC
        LIMIT 1
        """;

    // --- Ordini: ultimi 30 giorni per cliente ---
    public static final String LIST_RECENT_ORDERS_FOR_CLIENT =
        """
        SELECT Codiceordine, Data
        FROM ordine
        WHERE CF = ?
          AND Rimborsato = 0
          AND CAST(Data AS DATE) >= (CURDATE() - INTERVAL 30 DAY)
        ORDER BY Data DESC, Codiceordine DESC
        """;

    // --- Dettagli articoli in un ordine ---
    public static final String LIST_ORDER_ITEMS =
        """
        SELECT p.Nome, p.Tipologia, p.Importo
        FROM (
            SELECT Codiceprodotto, Codiceordine FROM biglietto
            UNION ALL
            SELECT Codiceprodotto, Codiceordine FROM articolo_personale
            UNION ALL
            SELECT Codiceprodotto, Codiceordine FROM articolo_generale
            UNION ALL
            SELECT Codiceprodotto, Codiceordine FROM visita_guidata
        ) x
        JOIN prodotto p ON p.Codiceprodotto = x.Codiceprodotto
        WHERE x.Codiceordine = ?
        """;

    // --- Totale ordine scontato ---
    public static final String ORDER_TOTAL_WITH_DISCOUNT =
        """
        SELECT o.Codiceordine,
               SUM(p.Importo) AS TotaleLordo,
               MAX(COALESCE(a.Sconto, CASE a.Tipo
                 WHEN 'completo' THEN 20
                 WHEN 'normale'  THEN 15
                 WHEN 'essenziale' THEN 10
                 ELSE 0 END, 0)) AS ScontoPerc,
               SUM(p.Importo) * (1 - (MAX(COALESCE(a.Sconto, CASE a.Tipo
                 WHEN 'completo' THEN 20
                 WHEN 'normale'  THEN 15
                 WHEN 'essenziale' THEN 10
                 ELSE 0 END, 0)) / 100.0)) AS TotaleNetto
        FROM ordine o
        JOIN (
            SELECT Codiceprodotto, Codiceordine FROM biglietto
            UNION ALL
            SELECT Codiceprodotto, Codiceordine FROM articolo_personale
            UNION ALL
            SELECT Codiceprodotto, Codiceordine FROM articolo_generale
            UNION ALL
            SELECT Codiceprodotto, Codiceordine FROM visita_guidata
        ) x ON x.Codiceordine = o.Codiceordine
        JOIN prodotto p ON p.Codiceprodotto = x.Codiceprodotto
        LEFT JOIN (
            SELECT CF, Anno, MAX(Sconto) AS Sconto, MAX(Tipodiabbonamento) AS Tipo
            FROM abbonamento
            GROUP BY CF, Anno
        ) a ON a.CF = o.CF AND a.Anno = CAST(SUBSTRING(o.Data, 1, 4) AS UNSIGNED)
        WHERE o.Codiceordine = ?
        GROUP BY o.Codiceordine
        """;

    // --- Reso: eliminazione/clear articoli e ordine ---
    public static final String REFUND_DELETE_ARTICOLO_PERSONALE =
        """
        DELETE FROM articolo_personale WHERE Codiceordine = ?
        """;
    public static final String REFUND_DELETE_ARTICOLO_GENERALE =
        """
        DELETE FROM articolo_generale WHERE Codiceordine = ?
        """;
    public static final String REFUND_CLEAR_BIGLIETTO =
        """
        UPDATE biglietto SET Codiceordine = NULL WHERE Codiceordine = ?
        """;
    public static final String REFUND_CLEAR_VISITA_GUIDATA =
        """
        DELETE FROM visita_guidata WHERE Codiceordine = ?
        """;
    public static final String REFUND_DELETE_ORDINE =
        """
        DELETE FROM ordine WHERE Codiceordine = ? AND CF = ?
        """;

    // --- Check: ordine contiene una visita guidata già passata? ---
    public static final String ORDER_HAS_PAST_VISIT =
        """
        SELECT 1
        FROM visita_guidata
        WHERE Codiceordine = ?
          AND DataVisita < CURDATE()
        LIMIT 1
        """;
}
