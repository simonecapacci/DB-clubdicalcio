-- FACTORY RESET + RECREATE (MySQL 5.7+)
-- ATTENZIONE: questo script **CANCELLA** tutte le tabelle nello schema `juventusfc` e le ricrea da zero.
-- Eseguilo solo se vuoi ripartire da zero. In caso di dubbio, fai prima un BACKUP.

CREATE DATABASE IF NOT EXISTS `juventusfc`;
USE `juventusfc`;

SET @OLD_FOREIGN_KEY_CHECKS := @@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS = 0;

-- Drop dinamico di tutte le tabelle nello schema corrente
SET @tables := NULL;
SELECT GROUP_CONCAT(CONCAT('`', table_name, '`') SEPARATOR ',') INTO @tables
FROM information_schema.tables
WHERE table_schema = DATABASE();

SET @sql := IFNULL(CONCAT('DROP TABLE IF EXISTS ', @tables), 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;

-- ====== RECREATION STARTS HERE ======
CREATE TABLE calciatore (
  CF CHAR(16) PRIMARY KEY,
  NumeroDiMaglia INT NOT NULL,
  Nome VARCHAR(50),
  Cognome VARCHAR(50),
  IDContratto INT
);
INSERT INTO calciatore (CF, NumeroDiMaglia, Nome, Cognome, IDContratto) VALUES
('RSSMRA85C01H501Z', 10, 'Marco', 'Rossi', 1),
('BNCLCU90A01F205X', 7, 'Luca', 'Bianchi', 2),
('FRNGPP95E05D612T', 11, 'Giuseppe', 'Ferrari', 3),
('VRDLNZ92H10C351K', 9, 'Lorenzo', 'Verdi', 4),
('NCLFNC87D20A662L', 1, 'Francesco', 'Nicolò', 5),
('MNTRIC88L13E783W', 6, 'Riccardo', 'Monti', 6),
('CLLMNL93T18F205Y', 4, 'Manuel', 'Collini', 7),
('RNCPLA91C07D969E', 5, 'Paolo', 'Roncaglia', 8),
('BLGMRZ96A01H501T', 3, 'Mirko', 'Bellagamba', 9),
('SGNLRA89E20A662M', 8, 'Leonardo', 'Signori', 10),
('FRLCST90B11F205V', 13, 'Cristian', 'Ferla', 11),
('SPNSMR94G19E783J', 14, 'Simone', 'Spinosa', 12),
('DRMGNN88D14H501U', 15, 'Gennaro', 'Di Roma', 13),
('PRTCMN97L01C351R', 16, 'Carmine', 'Poretto', 14),
('CSTFBL86M22A662H', 17, 'Fabio', 'Castelli', 15),
('LSNZNC91E03D612B', 18, 'Nicola', 'Lo Sano', 16),
('RZZLCA85T05F205Z', 19, 'Carlo', 'Rizzo', 17),
('MRTSLV90H04H501Y', 20, 'Salvatore', 'Martelli', 18),
('TRNCNL95C09C351Q', 21, 'Emanuele', 'Tornese', 19),
('RLLGNN93G16E783N', 22, 'Gianni', 'Rulli', 20);
CREATE TABLE `trasferimento` (
  `IDtrasferimento` int NOT NULL,
  `Clubcoinvolto` varchar(45) NOT NULL,
  `Valoretrasferimento` int NOT NULL,
  `Datatrasferimento` date NOT NULL,
  `Durataprestito` date DEFAULT NULL,
  `CF` varchar(45) NOT NULL,
  PRIMARY KEY (`IDtrasferimento`),
  UNIQUE KEY `IDtrasferimento_UNIQUE` (`IDtrasferimento`)
);
INSERT INTO trasferimento (IDtrasferimento, Clubcoinvolto, Valoretrasferimento, Datatrasferimento, Durataprestito, CF) VALUES
(1, 'Inter', 3500000, '2024-07-01', NULL, 'RSSMRA95L12F205A'),
(2, 'Milan', 2400000, '2023-08-15', '2024-08-15', 'VRDLNZ90D10H501B'),
(3, 'Roma', 5000000, '2022-01-10', NULL, 'BNCGPP99E45A794T'),
(4, 'Napoli', 1800000, '2023-07-20', '2024-01-20', 'CNTFNC93L13H703S'),
(5, 'Lazio', 6000000, '2024-06-30', NULL, 'SLCMHL88C20H501A'),
(6, 'Fiorentina', 3200000, '2022-07-05', '2023-01-05', 'ZMLMRA97A01H501F'),
(7, 'Torino', 2700000, '2023-09-01', NULL, 'CLCMRA93B10D612Y'),
(8, 'Atalanta', 4500000, '2023-02-10', '2023-08-10', 'RNBPLA89M10A326P'),
(9, 'Bologna', 3100000, '2024-01-15', NULL, 'FRNGNN98R12H501W'),
(10, 'Sassuolo', 3800000, '2023-07-01', '2024-07-01', 'MNTCRS94A10H501R'),
(11, 'Verona', 1500000, '2024-08-01', NULL, 'VRDLNZ90D10H501B'),
(12, 'Empoli', 1200000, '2022-09-10', '2023-03-10', 'RSSMRA95L12F205A'),
(13, 'Cagliari', 2900000, '2023-01-25', NULL, 'PLLCST97S20H703T'),
(14, 'Monza', 4200000, '2024-07-12', '2025-07-12', 'SLCMHL88C20H501A'),
(15, 'Lecce', 1100000, '2023-08-20', NULL, 'CNTFNC93L13H703S'),
(16, 'Genoa', 5000000, '2022-06-15', '2024-06-15', 'BNCGPP99E45A794T'),
(17, 'Salernitana', 2000000, '2023-07-25', NULL, 'VRDLNZ90D10H501B'),
(18, 'Parma', 1750000, '2024-07-05', '2025-01-05', 'FRNGNN98R12H501W'),
(19, 'Palermo', 1400000, '2022-08-10', NULL, 'MNTCRS94A10H501R'),
(20, 'Milan', 3200000, '2023-03-18', '2024-09-18', 'ZMLMRA97A01H501F'),
(21, 'Inter', 3200000, '2024-07-01', '2025-06-30', 'BNCGNT95C12F205X'),
(22, 'Torino', 2800000, '2024-07-03', '2025-05-15', 'VRDLGI97E01L219R'),
(23, 'Sassuolo', 2100000, '2024-07-05', '2025-01-10', 'RSSFNC91D08A456T'),
(24, 'Fiorentina', 2500000, '2024-07-07', '2025-06-01', 'TMRCLS99H20Z129Y');

CREATE TABLE `calciatorevenduto` (
  `CF` VARCHAR(45) NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Cognome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`CF`),
  UNIQUE INDEX `CF_UNIQUE` (`CF` ASC) VISIBLE);
INSERT INTO calciatorevenduto (CF, Nome, Cognome)
VALUES
('PLLCST97S20H703T', 'Paolo', 'Costa'),
('RNBPLA89M10A326P', 'Riccardo', 'Benelli'),
('CLCMRA93B10D612Y', 'Claudio', 'Camara'),
('ZMLMRA97A01H501F', 'Zeno', 'Milas');
CREATE TABLE `calciatorecedutoinprestito` (
  `CF` VARCHAR(45) NOT NULL,
  `Posizione` VARCHAR(45) NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Cognome` VARCHAR(45) NOT NULL,
  `IDcontratto` INT NOT NULL,
  PRIMARY KEY (`CF`),
  UNIQUE INDEX `CF_UNIQUE` (`CF` ASC) VISIBLE);
INSERT INTO calciatorecedutoinprestito (CF, Posizione, Nome, Cognome,IDcontratto)
VALUES
('BNCGNT95C12F205X', 'Difensore', 'Giovanni', 'Bianchi','21'),
('VRDLGI97E01L219R', 'Centrocampista', 'Luca', 'Verdelli','22'),
('RSSFNC91D08A456T', 'Attaccante', 'Marco', 'Russo','23'),
('TMRCLS99H20Z129Y', 'Portiere', 'Simone', 'Tamari','24');
CREATE TABLE `contratto` (
  `Idcontratto` INT NOT NULL,
  `Datastipulazione` DATE NOT NULL,
  `Durata` DATE NOT NULL,
  `Stipendio` INT NOT NULL,
  PRIMARY KEY (`Idcontratto`),
  UNIQUE INDEX `Idcontratto_UNIQUE` (`Idcontratto` ASC) VISIBLE);
CREATE TABLE `membrostaff` (
  `CF` VARCHAR(45) NOT NULL,
  `Ruolo` VARCHAR(45) NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Cognome` VARCHAR(45) NOT NULL,
  `IDcontratto` INT NOT NULL,
  PRIMARY KEY (`CF`),
  UNIQUE INDEX `CF_UNIQUE` (`CF` ASC) VISIBLE,
  UNIQUE INDEX `IDcontratto_UNIQUE` (`IDcontratto` ASC) VISIBLE);
INSERT INTO membrostaff (CF, Ruolo, Nome, Cognome, IDcontratto)
VALUES
('RSSMRA80A01H501F', 'Allenatore',         'Massimiliano', 'Rossi',        25),
('BNCGNE82C15L219K', 'Viceallenatore',     'Gianluca',      'Bianchi',      26),
('VRDLRA79D09T123J', 'Preparatore tecnico','Lorenzo',       'Verdone',      27),
('FRNLCU83A20F205Z', 'Preparatore tecnico','Francesco',     'Luciani',      28),
('MRTFNC85B30G702P', 'Preparatore tecnico','Matteo',        'Forti',        29),
('CLLMRA81C10A345Y', 'Fisioterapista',     'Marta',         'Celli',        30),
('GRNTNC84D12Z129W', 'Fisioterapista',     'Giulia',        'Granata',      31),
('TSTFNC86A03F841T', 'Fisioterapista',     'Tommaso',       'Testa',        32),
('LMRLCU87M12G843R', 'Preparatore tecnico','Luca',          'Lamaro',       33),
('MNTGRT78H22T678S', 'Preparatore tecnico','Giorgio',       'Monti',        34),
('BRTGNT90T01F205Q', 'Viceallenatore',     'Davide',        'Bertoni',      35),
('MCLMNL79S10L219V', 'Preparatore tecnico','Manuel',        'Macalli',      36),
('PLLMCL77R09A564K', 'Fisioterapista',     'Paolo',         'Pollini',      37),
('SLLGNT81T05T345P', 'Fisioterapista',     'Silvia',        'Sallusti',     38),
('RMLTNC83D18Z129X', 'Preparatore tecnico','Raffaele',      'Romani',       39);
CREATE TABLE `juventusfc`.`dirigente` (
  `CF` VARCHAR(45) NOT NULL,
  `Ruolo` VARCHAR(45) NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Cognome` VARCHAR(45) NOT NULL,
  `IDcontratto` INT NULL,
  `Pass` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `CF_UNIQUE` (`CF` ASC) VISIBLE,
  PRIMARY KEY (`CF`),
  UNIQUE INDEX `IDcontratto_UNIQUE` (`IDcontratto` ASC) VISIBLE);
INSERT INTO dirigente (CF, Ruolo, Nome, Cognome, IDcontratto, Pass)
VALUES
('AGNPRS70A01F205X', 'Presidente',             'Andrea',      'Agnelli',          null,		'euwh9fwh89vf'),
('DELFRN75B22C351M', 'Vice presidente',        'Franco',      'Della Rovere',     41,		'iowonavoi894'),
('MLRGRD80C11H702T', 'Direttore sportivo',     'Gabriele',    'Maldera',          42, 		'vherih04sdhv'),
('CNTLRA78D03F841L', 'Amministratore delegato','Lorenzo',     'Conti',            43,		'braerbe436bf'),
('RSPGRC74E09Z129P', 'Direttore generale',     'Giancarlo',   'Ruspa',            44,		'revivhr040kh'),
('TRNTNC82A16L219B', 'Direttore tecnico',      'Nicola',      'Trentini',         40,		'jdihw80h0id0');
CREATE TABLE `juventusfc`.`guida` (
  `CF` VARCHAR(45) NOT NULL,
  `Turnolavorativo` VARCHAR(45) NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Cognome` VARCHAR(45) NOT NULL,
  `IDcontratto` INT NOT NULL,
  PRIMARY KEY (`CF`),
  UNIQUE INDEX `CF_UNIQUE` (`CF` ASC) VISIBLE);
INSERT INTO guida (CF, Turnolavorativo, Nome, Cognome, IDcontratto)
VALUES
('LMBCRS85C01F205Z', 'Mattina',     'Carlo',     'Lombardi',    45),
('NGRFNC88B12G702Q', 'Pomeriggio',  'Francesca', 'Neri',        46),
('TRSMRC90A05H501W', 'Pomeriggio',       'Marco',     'Trisolini',   47),
('BRTPLA83M23Z129L', 'Mattina',     'Paola',     'Bartoli',     48),
('MNTMNL79T08L219C', 'Pomeriggio',  'Manuela',   'Montesi',     49);
INSERT INTO contratto (Idcontratto, Datastipulazione, Durata, Stipendio)
VALUES
(1, '2022-07-01', '2026-06-30', 2500000),
(2, '2023-01-15', '2027-01-14', 3200000),
(3, '2021-08-10', '2025-08-09', 4500000),
(4, '2020-09-01', '2024-08-31', 6000000),
(5, '2023-06-20', '2027-06-19', 1500000),
(6, '2022-07-01', '2025-06-30', 1800000),
(7, '2023-07-15', '2027-07-14', 3500000),
(8, '2021-08-20', '2025-08-19', 4000000),
(9, '2022-09-10', '2026-09-09', 2200000),
(10, '2023-01-01', '2026-12-31', 2700000),
(11, '2021-02-01', '2024-01-31', 3300000),
(12, '2023-08-01', '2027-07-31', 3900000),
(13, '2022-10-01', '2026-09-30', 2100000),
(14, '2021-07-01', '2025-06-30', 4800000),
(15, '2023-03-01', '2026-02-28', 5100000),
(16, '2022-01-01', '2024-12-31', 2700000),
(17, '2023-05-01', '2027-04-30', 3500000),
(18, '2021-11-01', '2025-10-31', 3000000),
(19, '2022-04-01', '2026-03-31', 3400000),
(20, '2023-06-01', '2026-05-31', 4600000),
(21, '2022-08-01', '2026-07-31', 4200000),
(22, '2021-09-01', '2025-08-31', 3700000),
(23, '2023-10-01', '2027-09-30', 4100000),
(24, '2022-12-01', '2026-11-30', 3800000),
(25, '2023-07-01', '2025-06-30', 500000),
(26, '2022-08-01', '2024-07-31', 450000),
(27, '2023-02-01', '2025-01-31', 400000),
(28, '2021-07-01', '2023-06-30', 300000),
(29, '2022-09-01', '2024-08-31', 350000),
(30, '2023-01-01', '2024-12-31', 250000),
(31, '2023-04-01', '2025-03-31', 200000),
(32, '2021-11-01', '2023-10-31', 150000),
(33, '2022-06-01', '2024-05-31', 380000),
(34, '2023-05-01', '2025-04-30', 420000),
(35, '2023-03-01', '2025-02-28', 370000),
(36, '2021-09-01', '2023-08-31', 160000),
(37, '2022-02-01', '2024-01-31', 280000),
(38, '2023-06-01', '2025-05-31', 360000),
(39, '2022-10-01', '2024-09-30', 300000),
(40, '2021-07-01', '2026-06-30', 950000),
(41, '2023-01-01', '2027-12-31', 850000),
(42, '2022-04-01', '2026-03-31', 800000),
(43, '2021-10-01', '2025-09-30', 700000),
(44, '2023-06-01', '2027-05-31', 750000),
(45, '2022-09-01', '2023-08-31', 28000),
(46, '2023-02-01', '2024-01-31', 30000),
(47, '2021-07-01', '2022-06-30', 27000),
(48, '2022-12-01', '2023-11-30', 25000),
(49, '2023-05-01', '2024-04-30', 32000);
CREATE TABLE `juventusfc`.`sponsor` (
  `Nomesocietà` VARCHAR(45) NOT NULL,
  `Codicesocietà` INT NOT NULL,
  `Compensoeconomico` INT NOT NULL,
  `Datastipulazione` DATE NOT NULL,
  `Durata` DATE NOT NULL,
  `CF` VARCHAR(45) NOT NULL,
  UNIQUE INDEX `Nomesocietà_UNIQUE` (`Nomesocietà` ASC) VISIBLE,
  PRIMARY KEY (`Codicesocietà`),
  UNIQUE INDEX `Codicesocietà_UNIQUE` (`Codicesocietà` ASC) VISIBLE);
INSERT INTO juventusfc.sponsor 
(Nomesocietà, Codicesocietà, Compensoeconomico, Datastipulazione, Durata, CF)
VALUES
('Adidas', 1001, 4500000, '2023-07-01', '2026-06-30', 'AGNPRS70A01F205X'),
('Jeep', 1002, 5200000, '2022-01-01', '2025-12-31', 'DELFRN75B22C351M'),
('Allianz', 1003, 3800000, '2021-09-01', '2024-08-31', 'MLRGRD80C11H702T'),
('Bitget', 1004, 2500000, '2023-03-15', '2026-03-14', 'CNTLRA78D03F841L'),
('Socios', 1005, 3100000, '2022-05-01', '2025-04-30', 'RSPGRC74E09Z129P'),
('Balocco', 1006, 2900000, '2023-11-01', '2026-10-31', 'TRNTNC82A16L219B');
CREATE TABLE `juventusfc`.`prodotto` (
  `Codiceprodotto` INT NOT NULL,
  `Importo` FLOAT NOT NULL,
  `Nome` VARCHAR(45) NULL,
  `Tipologia` VARCHAR(45) NULL,
  PRIMARY KEY (`Codiceprodotto`),
  UNIQUE INDEX `Codiceprodotto_UNIQUE` (`Codiceprodotto` ASC) VISIBLE);
CREATE TABLE `juventusfc`.`ordine` (
  `Codiceordine` INT NOT NULL,
  `Data` VARCHAR(45) NOT NULL,
  `Rimborsato` TINYINT NOT NULL,
  `CF` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Codiceordine`));
CREATE TABLE `juventusfc`.`cliente` (
  `CF` VARCHAR(45) NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Cognome` VARCHAR(45) NOT NULL,
  `Indirizzodispedizione` VARCHAR(45) NULL,
  `Mail` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`CF`),
  UNIQUE INDEX `CF_UNIQUE` (`CF` ASC) VISIBLE,
  UNIQUE INDEX `Mail_UNIQUE` (`Mail` ASC) VISIBLE);
INSERT INTO juventusfc.cliente (CF, Nome, Cognome, Indirizzodispedizione, Mail, Password) VALUES
('KABKVS21H78QY2PI', 'Sara', 'Neri', NULL, 'sara.neri18@email.com', 'hGz9FgMP'),
('UTHMVQ09Z78SIDQZ', 'Andrea', 'Calò', NULL, 'andrea.calò328@email.com', '7dE4oChd'),
('ORXHRB03B51LJIBV', 'Marco', 'Russo', 'Via Milano 88, Milano', 'marco.russo215@email.com', 'd6TW0cev'),
('MPVSED25H59H24VU', 'Antonio', 'Zamolo', NULL, 'antonio.zamolo609@email.com', 'absjYhcc'),
('YACSJS88O93JFEGJ', 'Andrea', 'Gentile', NULL, 'andrea.gentile7F71@email.com', '4sjQPKkv'),
('XKAKGK56J49AP11G', 'Silvia', 'Castelli', 'Via Balbi 3, Genova', 'silvia.castelli578@email.com', 'QyyhmhVC'),
('GYLXKB20Q53CQ54C', 'Costanza', 'Palazzi', 'Via dei Mille 21, Napoli', 'costanza.palazzi27@email.com', 'PVE3O8dk'),
('DSUBYP50S76Q3J9S', 'Paolo', 'Gentile', 'Via Milano 88, Milano', 'paolo.gentile873@email.com', 'DECnM83A'),
('MZLNAY70T87ZDSVU', 'Simona', 'Castelli', 'Via dei Mille 21, Napoli', 'simona.castelli740@email.com', 'pE8SYeHt'),
('DWFKVM65Y67FRVPK', 'Federico', 'Bianchi', 'Via Milano 88, Milano', 'federico.bianchi981@email.com', 'JegzYZaw'),
('EVMKTO21H06N1SGN', 'Laura', 'Verdi', 'Via Roma 12, Torino', 'laura.verdi582@email.com', 'rgzvFPvj'),
('PKXBLK35A45YV92J', 'Alessandro', 'Tarsia', 'Via San Donato 10, Torino', 'alessandro.tarsia975@email.com', 'mvenZ74j'),
('KYNNLX69D16TW69I', 'Francesca', 'Monti', 'Via Roma 12, Torino', 'francesca.monti243@email.com', '39LHQdn6'),
('COONWV75A82KR21T', 'Alessandro', 'Bianchi', 'Corso Francia 45, Torino', 'alessandro.bianchi124@email.com', 'ILfI419Y'),
('QMTWMF58S37V5SWL', 'Mirko', 'Pellecchia', 'Via dei Mille 21, Napoli', 'mirko.pellecchia884@email.com', '30ZmQQKT'),
('LBEEQK13I70K95CN', 'Francesca', 'Bianchi', 'Via Milano 88, Milano', 'francesca.bianchi408@email.com', 'FOH21ruM'),
('SWSOAF77T97LCSLV', 'Andrea', 'Neri', 'Via Po 16, Torino', 'andrea.neri477@email.com', 'CQYzhMWN'),
('QKHHLZ41S34OFMQS', 'Elena', 'Verdi', NULL, 'elena.verdi570@email.com', 'Dz4zLwCB'),
('UVXZGG80E09SVK5H', 'Alessandro', 'Castelli', NULL, 'alessandro.castelli622@email.com', 'U7kYu9oT'),
('FCIHTS20Q50A86XX', 'Marco', 'Greco', 'Via dei Mille 21, Napoli', 'marco.greco446@email.com', 'ZNNZF5dP'),
('OYIUCJ43X81QJ3JY', 'Marco', 'Rossi', NULL, 'marco.rossi227@email.com', 'Jl3MPEqk'),
('QOFSLY98G84O8SJX', 'Andrea', 'Marini', 'Via San Donato 10, Torino', 'andrea.marini601@email.com', 'mSkQgx1e'),
('NAKXBB22C51L0Q1K', 'Costanza', 'Monti', NULL, 'costanza.monti140@email.com', '5IBaZ2OZ'),
('YASKJB85Y60J6QIG', 'Elena', 'Fontana', NULL, 'elena.fontana955@email.com', 'p2cytaeo'),
('OIFEPN34V51Y7KLE', 'Alessandro', 'Greco', 'Via Napoli 34, Napoli', 'alessandro.greco486@email.com', 'ewIoMA6m'),
('KAWCTM14M10BI1VX', 'Simona', 'Palazzi', 'Via Po 16, Torino', 'simona.palazzi767@email.com', 'xqNO7SjZ'),
('BFMFES93V86HSE0N', 'Luigi', 'Castelli', NULL, 'luigi.castelli905@email.com', 'CxRC5e2y'),
('XCCNXQ79V36XXOEK', 'Antonio', 'Greco', 'Via Balbi 3, Genova', 'antonio.greco601@email.com', 'wFdItYoW'),
('NBSYAH40M68RDX7L', 'Francesca', 'Tamaro', NULL, 'francesca.tamaro136@email.com', 'bU2w669D'),
('ABKDVR04P99H0KET', 'Martina', 'Bianchi', 'Via dei Mille 21, Napoli', 'martina.bianchi629@email.com', 'qjsL8Xzc'),
('ESSLUL41O79JDYIH', 'Marco', 'Gentile', 'Via Roma 12, Torino', 'marco.gentile324@email.com', 'iT66lLuP'),
('DPATUN46L19VIICR', 'Alessandro', 'Neri', NULL, 'alessandro.neri497@email.com', 'AsUgFGEe'),
('HUOKSI25Y00XQIAS', 'Francesca', 'Greco', NULL, 'francesca.greco96@email.com', 'VbsqwJVK'),
('CFUYHM22Y53K3MHO', 'Federico', 'Tarsia', NULL, 'federico.tarsia586@email.com', '2JJWQLk0'),
('YNZWZC02D57NBABQ', 'Costanza', 'Tarsia', NULL, 'costanza.tarsia781@email.com', 'IKOrWPN3'),
('OLNOLH58L38IDS1A', 'Simona', 'Gentile', 'Via Balbi 3, Genova', 'simona.gentile543@email.com', 'tO1cNiHo'),
('ELIUJB70A47CKR6N', 'Costanza', 'Russo', NULL, 'costanza.russo474@email.com', 'U9HTu9YE'),
('YWSPLT53L01M4E4A', 'Laura', 'Martini', NULL, 'laura.martini606@email.com', 'eYFA7aEt'),
('ENWZRA66T81TPVSJ', 'Francesca', 'Zamolo', NULL, 'francesca.zamolo194@email.com', 'Ssq89OZw'),
('GUWIIJ18C74Y7SAW', 'Federico', 'Palazzi', NULL, 'federico.palazzi481@email.com', 'DId4OVos'),
('KLXCXY92M88L4MYH', 'Claudio', 'Russo', 'Via Roma 12, Torino', 'claudio.russo938@email.com', 'upKec0q0'),
('GFDTVY29I23LY8GL', 'Marco', 'Fontana', NULL, 'marco.fontana771@email.com', '3kS9HBce'),
('RYWEZH97Y30ETDOZ', 'Elena', 'Greco', NULL, 'elena.greco915@email.com', 'i7ETeyIF'),
('VZFSPU45Z59DY94S', 'Antonio', 'Greco', NULL, 'antonio.greco654@email.com', 'OO0TKtyy'),
('UDNXQL82N66A7KEW', 'Elena', 'Greco', NULL, 'elena.greco684@email.com', 'DWnhK0gv'),
('NCVWGB50W95LF30L', 'Maria', 'Verdi', NULL, 'maria.verdi319@email.com', 'pCCsuRTF'),
('HOMDBG59N19GFG8T', 'Luigi', 'Castelli', 'Via Napoli 34, Napoli', 'luigi.castelli100@email.com', 'vSEpm8IG'),
('VWLIFV19L65ZZDFB', 'Costanza', 'Monti', NULL, 'costanza.monti627@email.com', 'XPR4ap60'),
('SLPLAG06N98YZVSY', 'Mirko', 'Gentile', NULL, 'mirko.gentile951@email.com', '4mc2bOCh'),
('RYDQOM65G91SYPIR', 'Maria', 'Zamolo', 'Via Balbi 3, Genova', 'maria.zamolo857@email.com', 'PanQicAA'),
('FAGTSJ02J08A2WHF', 'Antonio', 'Neri', 'Via dei Mille 21, Napoli', 'antonio.neri81@email.com', '1Fh5eH7S'),
('ZTMAUH13P45N8WBT', 'Laura', 'Tarsia', NULL, 'laura.tarsia152@email.com', 'ss90E8VM'),
('TXLVQQ37Q53JQ8NC', 'Andrea', 'Greco', 'Via Balbi 3, Genova', 'andrea.greco791@email.com', 'b32j4nPf'),
('EBCLTK00D24DK8MF', 'Antonio', 'Gentile', NULL, 'antonio.gentile58@email.com', 'SD4Xw7CR'),
('EHQOAZ89R73XGTVS', 'Simona', 'Fontana', NULL, 'simona.fontana6@email.com', 'TtZQW0cB'),
('AEQHNO73J45OY0BT', 'Simona', 'Neri', NULL, 'simona.neri47@email.com', 'I4V1UZvU'),
('NNTNIF18L50X685D', 'Marco', 'Gentile', 'Via dei Mille 21, Napoli', 'marco.gentile5@email.com', 'zOMxrXv6'),
('UAEDHN99J77B2TPX', 'Giulia', 'Neri', 'Via Milano 88, Milano', 'giulia.neri830@email.com', 'z9YHOzqG'),
('LAMAIS76P66DQ26V', 'Antonio', 'Monti', 'Via dei Mille 21, Napoli', 'antonio.monti438@email.com', 'WaUaUbAv'),
('WOVUPI75I95R39FO', 'Alessandro', 'Tamaro', NULL, 'alessandro.tamaro243@email.com', 'sAHuElg9'),
('ZVVGCP78A69DU2AG', 'Francesca', 'Tamaro', NULL, 'francesca.tamaro641@email.com', 'C4m0ki7L'),
('UNCNAW57F09QLV4B', 'Elena', 'Neri', NULL, 'elena.neri60@email.com', 'g3qlvlD5'),
('NUHWLP82Z77H8I1O', 'Giulia', 'Marini', 'Via Milano 88, Milano', 'giulia.marini642@email.com', '8WyC6tK4'),
('YMUBMF12P48C6GRX', 'Laura', 'Monti', NULL, 'laura.monti826@email.com', 'EwX60LE9'),
('NDHLFJ09C68QEMGN', 'Simona', 'Calò', NULL, 'simona.calò2@email.com', 'YHzO1Jkm'),
('LBBMNY26Q21RUUIA', 'Luigi', 'Pellecchia', 'Via Napoli 34, Napoli', 'luigi.pellecchia740@email.com', 'FN3YUv8y'),
('CWMHZE18G87V4NZE', 'Antonio', 'Castelli', NULL, 'antonio.castelli339@email.com', 'JF0a4JmN'),
('OCDWJN63F42UA9IY', 'Claudio', 'Greco', 'Via Milano 88, Milano', 'claudio.greco53@email.com', 'NkC5etsw'),
('PYZMXQ03B99MWWCU', 'Laura', 'Zamolo', 'Via Roma 12, Torino', 'laura.zamolo85@email.com', 'ZoagiR7t'),
('GESTIV65H19WXN3K', 'Silvia', 'Pellecchia', 'Via Napoli 34, Napoli', 'silvia.pellecchia812@email.com', 'yf4i9QQY'),
('SFKYCM61V12LZQCH', 'Laura', 'Neri', NULL, 'laura.neri590@email.com', 'ZJAtpwod'),
('GMKURZ60Z89U956J', 'Laura', 'Neri', 'Via Po 16, Torino', 'laura.neri307@email.com', 'cP6QrlNr'),
('WSHVFP44C96AYKPX', 'Silvia', 'Castelli', 'Via Napoli 34, Napoli', 'silvia.castelli527@email.com', 'dK9H5yMx'),
('ZRNJJI68P41L760N', 'Giulia', 'Tarsia', NULL, 'giulia.tarsia693@email.com', '8JdzKAN8'),
('KPZPBJ37T14S1GDK', 'Marco', 'Bianchi', 'Via dei Mille 21, Napoli', 'marco.bianchi128@email.com', 'iYzmN7TP'),
('XEILGX63E21UJ1DJ', 'Claudio', 'Verdi', NULL, 'claudio.verdi517@email.com', 'vaMMMMyP'),
('BFDVIC00Q13E4DMN', 'Giulia', 'Gentile', 'Via Milano 88, Milano', 'giulia.gentile161@email.com', 'mWZWX110'),
('CZTGMV80Q18UEK5Q', 'Silvia', 'Marini', NULL, 'silvia.marini649@email.com', 'xWccy9q1'),
('SLOLHU46J63AQBRF', 'Marco', 'Castelli', NULL, 'marco.castelli666@email.com', 'ClszN7o7'),
('CPMKEA93Y26SOSLO', 'Laura', 'Tarsia', 'Via Milano 88, Milano', 'laura.tarsia761@email.com', 'Q5BsPKpQ'),
('EJEOWT68X45R7MRU', 'Martina', 'Greco', 'Corso Francia 45, Torino', 'martina.greco866@email.com', 'WhDELir7'),
('YZPMOF89G51S47IU', 'Paolo', 'Esposito', NULL, 'paolo.esposito394@email.com', '0JaoHIY1'),
('BJSAEA03W56NRIYZ', 'Elena', 'Rossi', NULL, 'elena.rossi205@email.com', 's5M1trob'),
('TKQRWW32Y07CVNII', 'Martina', 'Fontana', 'Corso Francia 45, Torino', 'martina.fontana313@email.com', 'ZSXE0kIm'),
('XZJCIR26C47HBGPE', 'Mirko', 'Rossi', NULL, 'mirko.rossi838@email.com', 'cSuapajO'),
('DWSQUZ95D11II9AQ', 'Giovanni', 'Calò', 'Via Roma 12, Torino', 'giovanni.calò439@email.com', 'Cs1cxqpB'),
('JUVUMT23H86O3Z7P', 'Antonio', 'Martini', NULL, 'antonio.martini80@email.com', '4pnEwWul'),
('VNBXIE38E92QAMUY', 'Francesca', 'Martini', 'Via Po 16, Torino', 'francesca.martini923@email.com', 'PUs6TUjj'),
('CDXTRP92I49WTUDC', 'Costanza', 'Calò', NULL, 'costanza.calò138@email.com', 'i6yIGeZi'),
('STJSQJ53Y18XH9LI', 'Laura', 'Neri', NULL, 'laura.neri293@email.com', 'dRMcFb75'),
('MAUYNR58A11MWK4G', 'Giovanni', 'Russo', 'Via San Donato 10, Torino', 'giovanni.russo962@email.com', 'lSFH5eRe'),
('AOBDKF14U78QQRWO', 'Laura', 'Gentile', 'Via dei Mille 21, Napoli', 'laura.gentile574@email.com', 'KSY38OPt'),
('KGOZFR84J51VCDCM', 'Andrea', 'Palazzi', 'Corso Francia 45, Torino', 'andrea.palazzi127@email.com', 'KgJW3oWi'),
('PDFTLC54Y99XMPWX', 'Andrea', 'Fontana', NULL, 'andrea.fontana658@email.com', 'HgAghwJG'),
('JPENXZ24A53S42YZ', 'Giovanni', 'Gentile', 'Via San Donato 10, Torino', 'giovanni.gentile216@email.com', '4ccIOUEp'),
('PJPGGE34R33H43YZ', 'Sara', 'Marini', NULL, 'sara.marini512@email.com', 'KXI82ZCO'),
('UVOFRS86N34EHRQN', 'Elena', 'Fontana', 'Via Po 16, Torino', 'elena.fontana751@email.com', 'oLwB067R'),
('IHLAYP29D49E970T', 'Luigi', 'Fontana', 'Corso Francia 45, Torino', 'luigi.fontana230@email.com', 'DuQ2FUiw'),
('CLSEAW80R30OU30T', 'Giovanni', 'Fontana', 'Via Roma 12, Torino', 'giovanni.fontana241@email.com', 'z06wgJM8'),
('IYWBIU40G27FX2MT', 'Paolo', 'Monti', 'Via Napoli 34, Napoli', 'paolo.monti2@email.com', '3mfVAAVK');
INSERT INTO juventusfc.ordine (Codiceordine, Data, Rimborsato, CF) VALUES
(1, '2022-12-29', 1, 'GVQNFY75M80PPU4R') ,
(2, '2025-05-08', 1, 'UBDQAP46X74PJWDI') ,
(3, '2022-11-07', 1, 'BEPYGM68V11YJJVP') ,
(4, '2025-03-13', 1, 'RPVXRY59G48ZLU9P') ,
(5, '2023-04-27', 0, 'FSWLJL33H80MYZGY') ,
(6, '2024-11-09', 0, 'VWIGRZ65X86UIKJW') ,
(7, '2022-11-24', 0, 'ZIGPZI73G68M9SNA') ,
(8, '2025-06-28', 0, 'SQCROL66S48I3TNS') ,
(9, '2022-11-23', 0, 'UQDEMY44N93CM8MQ') ,
(10, '2023-07-22', 0, 'ITAJSR22E65NZWXB') ,
(11, '2024-07-01', 0, 'MPLJFD43G78Y83HG') ,
(12, '2023-08-23', 0, 'CFRLCV11X42Q302A') ,
(13, '2025-04-18', 0, 'QHIOSU50G47HR60D') ,
(14, '2023-10-04', 0, 'KGPCNZ26L00ADXLK') ,
(15, '2024-08-30', 0, 'GHLQZM30K76Z2DTL') ,
(16, '2023-08-11', 0, 'UUARCX60C83M1ENZ') ,
(17, '2025-03-25', 0, 'GDVMQO72V65QN4PN') ,
(18, '2024-01-09', 0, 'LNMEJA57V42H3X7T') ,
(19, '2023-05-10', 0, 'LNMEJA57V42H3X7T') ,
(20, '2024-12-21', 0, 'QJPOYI53O70NRUSU') ,
(21, '2025-04-22', 0, 'TPOFHD96R61ZJ3QD') ,
(22, '2023-01-18', 0, 'LIGIHL53Q16DN1IT') ,
(23, '2024-11-23', 0, 'JCGBVP61H11H3YXQ') ,
(24, '2023-12-16', 0, 'MYNZCC44P70U389X') ,
(25, '2022-10-21', 0, 'VHJHJB64M13BZPDH') ,
(26, '2024-01-04', 0, 'FSWLJL33H80MYZGY') ,
(27, '2022-12-31', 0, 'GHLQZM30K76Z2DTL') ,
(28, '2024-06-06', 0, 'LFWOBY14L78T2ZEP') ,
(29, '2023-08-11', 0, 'TWGYNI83T01XDSWN') ,
(30, '2024-07-13', 0, 'OOICFW03W15CWLPK') ,
(31, '2024-08-24', 0, 'RURTLC54O70HGK2W') ,
(32, '2023-04-24', 0, 'QFUTIS99E52S607Q') ,
(33, '2025-01-29', 0, 'HASHPX88I92HWV7U') ,
(34, '2025-02-20', 0, 'QAIVJK84P32XB22D') ,
(35, '2024-05-10', 0, 'UBDQAP46X74PJWDI') ,
(36, '2025-06-25', 0, 'JCGOFK86H03N1QGX') ,
(37, '2024-08-17', 0, 'ETDVLY18L66XUSVN') ,
(38, '2025-02-01', 0, 'GEKMHY37F87YB80Y') ,
(39, '2023-12-01', 0, 'APNWZZ86F56TEUAP') ,
(40, '2025-02-08', 0, 'ZNTNTH81H43M6DWE') ,
(41, '2023-02-08', 0, 'NARGKL35Q34OC2TA') ,
(42, '2024-09-17', 0, 'ITAJSR22E65NZWXB') ,
(43, '2023-06-17', 0, 'QVQXDF78D33NDA3T') ,
(44, '2024-10-03', 0, 'JBUSOV94E94WDKGE') ,
(45, '2022-11-22', 0, 'DGJMMB44J72DXOWW') ,
(46, '2024-07-24', 0, 'TPOFHD96R61ZJ3QD') ,
(47, '2024-09-20', 0, 'OEKZCV17M82OPU3J') ,
(48, '2024-06-07', 0, 'OOICFW03W15CWLPK') ,
(49, '2025-05-27', 0, 'FSIAIK92N92QOVEH') ,
(50, '2023-02-15', 0, 'BCZJLG35S65LS3UG') ,
(51, '2025-04-30', 0, 'PZNIZY45B55L2Z2Y') ,
(52, '2024-06-21', 0, 'SQCROL66S48I3TNS') ,
(53, '2024-06-30', 0, 'APNWZZ86F56TEUAP') ,
(54, '2023-03-03', 0, 'LZZBSM37W63RUF5A') ,
(55, '2023-11-26', 0, 'OEKZCV17M82OPU3J') ,
(56, '2024-11-12', 0, 'GGLNPT33I03NAM4H') ,
(57, '2024-07-07', 0, 'QAIVJK84P32XB22D') ,
(58, '2025-01-27', 0, 'WTTNZS30D83CAESX') ,
(59, '2023-03-28', 0, 'PVYTHA18M43H7SXY') ,
(60, '2025-06-23', 0, 'LHDJEM19S39NPOKR') ,
(61, '2022-12-27', 0, 'TPOFHD96R61ZJ3QD') ,
(62, '2023-12-19', 0, 'PZNIZY45B55L2Z2Y') ,
(63, '2023-06-17', 0, 'DGJMMB44J72DXOWW') ,
(64, '2024-01-23', 0, 'ITAJSR22E65NZWXB') ,
(65, '2023-11-20', 0, 'IUOFCU85I95GTZ5X') ,
(66, '2024-08-14', 0, 'LIGIHL53Q16DN1IT') ,
(67, '2023-09-02', 0, 'LFWOBY14L78T2ZEP') ,
(68, '2025-03-16', 0, 'FSIAIK92N92QOVEH') ,
(69, '2023-11-01', 0, 'QCYEOW65X72VJE3G') ,
(70, '2024-03-24', 0, 'IDODWG65E10RDF4K') ,
(71, '2023-09-05', 0, 'GCSZIC17Y88R1DGK') ,
(72, '2024-05-27', 0, 'ENZTNM98V94J9IRF') ,
(73, '2024-11-07', 0, 'TJHEIS94L64K4RAX') ,
(74, '2023-02-21', 0, 'BCZJLG35S65LS3UG') ,
(75, '2024-03-22', 0, 'TPRYFB44H30E1JQL') ,
(76, '2023-03-27', 0, 'LFWOBY14L78T2ZEP') ,
(77, '2024-07-02', 0, 'SKCCMS95U30M878R') ,
(78, '2023-11-06', 0, 'GEKMHY37F87YB80Y') ,
(79, '2024-12-11', 0, 'LHDJEM19S39NPOKR') ,
(80, '2024-02-20', 0, 'GDVMQO72V65QN4PN') ,
(81, '2023-09-08', 0, 'EVPITA78Z31OGI2B') ,
(82, '2023-11-29', 0, 'KGACPE78Z56WNLJI') ,
(83, '2023-09-07', 0, 'WNMUQQ56S99B91MF') ,
(84, '2024-07-10', 0, 'TJHEIS94L64K4RAX') ,
(85, '2024-02-21', 0, 'FSWLJL33H80MYZGY') ,
(86, '2025-06-14', 0, 'UQDEMY44N93CM8MQ') ,
(87, '2025-01-30', 0, 'CWNRDJ76H06UBM4J') ,
(88, '2024-08-31', 0, 'NZYFMA70W26X404M') ,
(89, '2025-04-22', 0, 'TWGYNI83T01XDSWN') ,
(90, '2022-12-26', 0, 'TAFAND69O23ZG6EA') ,
(91, '2024-12-10', 0, 'QVQXDF78D33NDA3T') ,
(92, '2025-03-04', 0, 'FSWLJL33H80MYZGY') ,
(93, '2024-03-26', 0, 'KGPCNZ26L00ADXLK') ,
(94, '2024-06-01', 0, 'USQMOP85S05V0WZE') ,
(95, '2023-02-27', 0, 'XBHXJW28U69AJCSQ') ,
(96, '2024-12-05', 0, 'GDVMQO72V65QN4PN') ,
(97, '2024-12-03', 0, 'QFUTIS99E52S607Q') ,
(98, '2025-01-30', 0, 'HTKWIX71X90LU1BT') ,
(99, '2023-04-26', 0, 'OBHKXI70T25MG6ZA') ,
(100, '2024-10-27', 0, 'TAFAND69O23ZG6EA') ,
(101, '2024-08-19', 0, 'SKCCMS95U30M878R') ,
(102, '2022-12-22', 0, 'MYNZCC44P70U389X') ,
(103, '2023-01-26', 0, 'GCSZIC17Y88R1DGK') ,
(104, '2024-04-18', 0, 'JQTUHA65N87R2U6F') ,
(105, '2025-03-02', 0, 'BEPYGM68V11YJJVP') ,
(106, '2024-01-03', 0, 'ITAJSR22E65NZWXB') ,
(107, '2023-04-04', 0, 'PNZCRT62M38D67KR') ,
(108, '2023-06-25', 0, 'ZCZNYH31M79YCRHY') ,
(109, '2022-10-31', 0, 'TWGYNI83T01XDSWN') ,
(110, '2024-08-02', 0, 'UUARCX60C83M1ENZ') ,
(111, '2024-11-15', 0, 'KGACPE78Z56WNLJI') ,
(112, '2023-06-12', 0, 'GZCDZB17B31K22CC') ,
(113, '2024-05-27', 0, 'FLCLUV61U92S7M2Z') ,
(114, '2023-05-02', 0, 'SSEWTK47D15GIX2Q') ,
(115, '2023-06-15', 0, 'ZJUSKB82J54J931H') ,
(116, '2023-08-28', 0, 'JCGOFK86H03N1QGX') ,
(117, '2025-01-07', 0, 'ZNTNTH81H43M6DWE') ,
(118, '2025-02-19', 0, 'LHDJEM19S39NPOKR') ,
(119, '2023-07-27', 0, 'HASHPX88I92HWV7U') ,
(120, '2023-12-28', 0, 'CMSNIU34C23EQ5FW') ,
(121, '2023-10-06', 0, 'QVQXDF78D33NDA3T') ,
(122, '2023-06-30', 0, 'SQCROL66S48I3TNS') ,
(123, '2023-10-01', 0, 'GEKMHY37F87YB80Y') ,
(124, '2025-02-21', 0, 'HASHPX88I92HWV7U') ,
(125, '2023-04-26', 0, 'ENZTNM98V94J9IRF') ,
(126, '2023-01-11', 0, 'LNMEJA57V42H3X7T') ,
(127, '2024-12-21', 0, 'KGPCNZ26L00ADXLK') ,
(128, '2024-01-12', 0, 'MNISDL30U00VBTAP') ,
(129, '2024-09-02', 0, 'OFFBPA17K01AKBFQ') ,
(130, '2024-07-05', 0, 'LIGIHL53Q16DN1IT') ,
(131, '2023-09-21', 0, 'MNISDL30U00VBTAP') ,
(132, '2023-09-28', 0, 'LLEAEF32K22C4Z3K') ,
(133, '2023-12-19', 0, 'OFFBPA17K01AKBFQ') ,
(134, '2023-05-22', 0, 'JCZRTQ72U57LTDCD') ,
(135, '2023-08-15', 0, 'GGLNPT33I03NAM4H') ,
(136, '2023-02-24', 0, 'SEPOKD72V52AYY4N') ,
(137, '2024-06-16', 0, 'CWNRDJ76H06UBM4J') ,
(138, '2023-10-07', 0, 'TJHEIS94L64K4RAX') ,
(139, '2023-07-03', 0, 'QJPOYI53O70NRUSU') ,
(140, '2024-10-06', 0, 'RURTLC54O70HGK2W') ,
(141, '2023-02-08', 0, 'GVQNFY75M80PPU4R') ,
(142, '2025-06-02', 0, 'QFUTIS99E52S607Q') ,
(143, '2022-10-27', 0, 'KGACPE78Z56WNLJI') ,
(144, '2024-03-09', 0, 'ZCQUIG28I52U98LV') ,
(145, '2024-01-25', 0, 'CWNRDJ76H06UBM4J') ,
(146, '2023-08-07', 0, 'HQONSQ88I81DZDCE') ,
(147, '2025-05-19', 0, 'AGQJZY12F29U60DA') ,
(148, '2025-04-30', 0, 'OOICFW03W15CWLPK') ,
(149, '2024-07-27', 0, 'LLEAEF32K22C4Z3K') ,
(150, '2023-08-04', 0, 'IDODWG65E10RDF4K') ;
INSERT INTO juventusfc.prodotto (Codiceprodotto, Importo, Tipologia, Nome) VALUES
(1, 35.00, "Articologenerale", "Zaini e borse sportive"),
(2, 5.00, "Articologenerale", "Portachiavi con logo"),
(3, 20.00, "Articologenerale", "Portafogli"),
(4, 3.50, "Articologenerale", "Braccialetti e laccetti da collo"),
(5, 15.00, "Articologenerale", "Cover per smartphone"),
(6, 60.00, "Articologenerale", "Orologi o smartband brandizzati"),
(7, 10.00, "Articologenerale", "Tazze e bicchieri"),
(8, 12.00, "Articologenerale", "Bottiglie e borracce"),
(9, 25.00, "Articologenerale", "Cuscini e plaid"),
(10, 18.00, "Articologenerale", "Poster e quadri con stadio/squadra"),
(11, 20.00, "Articologenerale", "Sveglie e calendari"),
(12, 8.00, "Articologenerale", "Set da scrivania (penne, quaderni, mousepad)"),
(13, 30.00, "Articologenerale", "Palloni ufficiali"),
(14, 12.00, "Articologenerale", "Scarpette in miniatura / memorabilia"),
(15, 7.00, "Articologenerale", "Fasce da capitano"),
(16, 25.00, "Articologenerale", "Guantoni da portiere (versione fan)"),
(17, 120.00, "Articolopersonale", "Maglietta ufficiale"),
(18, 45.00, "Articolopersonale", "Pantaloncini ufficiale"),
(19, 150.00, "Biglietto", "Biglietto partita vs Milan"),
(20, 150.00, "Biglietto", "Biglietto partita vs Inter"),
(21, 150.00, "Biglietto", "Biglietto partita vs Napoli"),
(22, 30.00, "Visitaguidata", "Visita guidata");

CREATE TABLE `juventusfc`.`abbonamento` (
  `IDabbonamento` INT NOT NULL,
  `Anno` INT NOT NULL,
  `Tipodiabbonamento` VARCHAR(45) NOT NULL,
  `Sconto` FLOAT NOT NULL DEFAULT 0,
  PRIMARY KEY (`IDabbonamento`),
  UNIQUE INDEX `IDabbonamento_UNIQUE` (`IDabbonamento` ASC) VISIBLE);
ALTER TABLE abbonamento
ADD COLUMN CF VARCHAR(45) NOT NULL;
INSERT INTO juventusfc.abbonamento (IDabbonamento, Anno, Tipodiabbonamento, CF)
VALUES
(1, 2024, 'completo', 'NZYFMA70W26X404M'),
(2, 2023, 'normale', 'FSIAIK92N92QOVEH'),
(3, 2022, 'essenziale', 'TPRYFB44H30E1JQL'),
(4, 2023, 'normale', 'MNISDL30U00VBTAP'),
(5, 2024, 'completo', 'RURTLC54O70HGK2W'),
(6, 2023, 'essenziale', 'JCGBVP61H11H3YXQ'),
(7, 2022, 'normale', 'FYQYRG14D40Q4LMU'),
(8, 2023, 'completo', 'ITAJSR22E65NZWXB'),
(9, 2022, 'normale', 'MNISDL30U00VBTAP'),
(10, 2024, 'essenziale', 'VAUULE31N03KYS1B'),
(11, 2023, 'completo', 'TPRYFB44H30E1JQL'),
(12, 2024, 'normale', 'FSIAIK92N92QOVEH'),
(13, 2022, 'essenziale', 'JCGBVP61H11H3YXQ'),
(14, 2024, 'completo', 'OBHKXI70T25MG6ZA'),
(15, 2023, 'normale', 'ZIGPZI73G68M9SNA'),
(16, 2022, 'essenziale', 'ITAJSR22E65NZWXB'),
(17, 2023, 'normale', 'FYQYRG14D40Q4LMU'),
(18, 2022, 'completo', 'RURTLC54O70HGK2W'),
(19, 2024, 'essenziale', 'JCGBVP61H11H3YXQ'),
(20, 2023, 'normale', 'MNISDL30U00VBTAP'),
(21, 2023, 'completo', 'ZIGPZI73G68M9SNA'),
(22, 2022, 'essenziale', 'OBHKXI70T25MG6ZA'),
(23, 2024, 'normale', 'FYQYRG14D40Q4LMU'),
(24, 2022, 'completo', 'TPRYFB44H30E1JQL'),
(25, 2023, 'essenziale', 'JCGBVP61H11H3YXQ'),
(26, 2022, 'normale', 'FSIAIK92N92QOVEH'),
(27, 2023, 'completo', 'MNISDL30U00VBTAP'),
(28, 2024, 'essenziale', 'ITAJSR22E65NZWXB'),
(29, 2023, 'normale', 'ZIGPZI73G68M9SNA'),
(30, 2022, 'completo', 'RURTLC54O70HGK2W'),
(31, 2023, 'essenziale', 'FYQYRG14D40Q4LMU'),
(32, 2024, 'normale', 'JCGBVP61H11H3YXQ'),
(33, 2022, 'completo', 'NZYFMA70W26X404M'),
(34, 2023, 'essenziale', 'TPRYFB44H30E1JQL'),
(35, 2024, 'normale', 'VAUULE31N03KYS1B'),
(36, 2022, 'completo', 'OBHKXI70T25MG6ZA'),
(37, 2024, 'essenziale', 'FSIAIK92N92QOVEH'),
(38, 2023, 'normale', 'MNISDL30U00VBTAP'),
(39, 2022, 'completo', 'ZIGPZI73G68M9SNA'),
(40, 2024, 'essenziale', 'JCGBVP61H11H3YXQ'),
(41, 2023, 'normale', 'TPRYFB44H30E1JQL'),
(42, 2022, 'completo', 'RURTLC54O70HGK2W'),
(43, 2024, 'essenziale', 'FYQYRG14D40Q4LMU'),
(44, 2023, 'normale', 'VAUULE31N03KYS1B'),
(45, 2022, 'completo', 'OBHKXI70T25MG6ZA'),
(46, 2023, 'essenziale', 'ITAJSR22E65NZWXB'),
(47, 2024, 'normale', 'JCGBVP61H11H3YXQ'),
(48, 2022, 'completo', 'NZYFMA70W26X404M'),
(49, 2023, 'essenziale', 'FSIAIK92N92QOVEH'),
(50, 2022, 'normale', 'TPRYFB44H30E1JQL');




/* =====================
   POST-RECREATE: Caso C per ORDINI ORFANI
   - Reindirizza gli ordini con CF non presente in cliente verso un CF valido
   - Ricrea la FK ordine(CF) -> cliente(CF) senza errori
   ===================== */

USE `juventusfc`;

START TRANSACTION;

-- Normalizza eventuali spazi / stringhe vuote
UPDATE ordine SET CF = NULLIF(TRIM(CF), '');

-- Scegli CF di destinazione: prova quello desiderato, altrimenti uno qualsiasi esistente;
-- se 'cliente' fosse vuota (caso limite), crea un placeholder.
SET @TARGET_CF := 'KABKVS21H78QY2PI';

-- Crea placeholder solo se la tabella cliente è vuota
INSERT INTO cliente (CF, Nome, Cognome, Indirizzodispedizione, Mail, Password)
SELECT 'PLCHLDR000000001', 'Da', 'Completare', NULL, 'placeholder@example.com', 'temp'
WHERE NOT EXISTS (SELECT 1 FROM cliente);

-- Scegli CF effettivo
SELECT COALESCE(
  (SELECT CF FROM cliente WHERE CF = @TARGET_CF LIMIT 1),
  (SELECT CF FROM cliente ORDER BY CF LIMIT 1)
) INTO @TARGET_CF_EFF;

-- Reindirizza gli ordini con CF non presente in cliente
UPDATE ordine o
LEFT JOIN cliente c ON c.CF = o.CF
SET o.CF = @TARGET_CF_EFF
WHERE c.CF IS NULL;

-- Drop FK esistente (se presente) e ricrea correttamente
SET @fk_exists := (
  SELECT COUNT(*) FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS
  WHERE CONSTRAINT_SCHEMA = DATABASE()
    AND CONSTRAINT_NAME = 'fk_ordine_cliente'
);
SET @sql := IF(@fk_exists > 0,
  'ALTER TABLE ordine DROP FOREIGN KEY fk_ordine_cliente',
  'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

ALTER TABLE ordine
  ADD CONSTRAINT fk_ordine_cliente
  FOREIGN KEY (CF) REFERENCES cliente(CF)
  ON DELETE RESTRICT ON UPDATE CASCADE;

COMMIT;

-- Verifica: devono essere 0
SELECT COUNT(*) AS orfani_residui
FROM ordine o
LEFT JOIN cliente c ON c.CF = o.CF
WHERE c.CF IS NULL;


/* =======================================================
   AGGIUNTA TABELLE MANCANTI + SEED MINIMO (idempotente)
======================================================= */
USE `juventusfc`;

-- 1) TABELLE
CREATE TABLE IF NOT EXISTS stagione (
  Anno INT PRIMARY KEY,
  PosizioneClassifica INT NULL,
  EsitoCoppaNazionale VARCHAR(100) NULL,
  EsitoCoppaEuropea   VARCHAR(100) NULL
);

CREATE TABLE IF NOT EXISTS squadra (
  IDSquadra INT PRIMARY KEY AUTO_INCREMENT,
  Nome VARCHAR(100) NOT NULL,
  TrofeiVinti INT DEFAULT 0,
  Stagione INT NULL,
  CONSTRAINT fk_squadra_stagione FOREIGN KEY (Stagione) REFERENCES stagione(Anno) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS partita (
  IDPartita INT PRIMARY KEY AUTO_INCREMENT,
  Data DATE NOT NULL,
  SquadraAvversaria VARCHAR(100) NOT NULL,
  Competizione VARCHAR(100) NOT NULL,
  Risultato VARCHAR(15) NULL,
  AnnoStagione INT NOT NULL,
  InCasa TINYINT NULL,
  CONSTRAINT fk_partita_stagione FOREIGN KEY (AnnoStagione) REFERENCES stagione(Anno) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS presenze (
  CF VARCHAR(45) NOT NULL,
  IDPartita INT NOT NULL,
  PRIMARY KEY (CF, IDPartita),
  CONSTRAINT fk_presenze_cliente FOREIGN KEY (CF) REFERENCES cliente(CF) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_presenze_partita FOREIGN KEY (IDPartita) REFERENCES partita(IDPartita) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS biglietto (
  Codiceprodotto INT PRIMARY KEY,
  IDPartita INT NOT NULL,
  CONSTRAINT fk_biglietto_prodotto FOREIGN KEY (Codiceprodotto) REFERENCES prodotto(Codiceprodotto) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_biglietto_partita FOREIGN KEY (IDPartita) REFERENCES partita(IDPartita) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS visita_guidata (
  Codiceprodotto INT PRIMARY KEY,
  DataVisita DATE NOT NULL,
  CFGuida VARCHAR(45) NULL,
  CONSTRAINT fk_visita_prodotto FOREIGN KEY (Codiceprodotto) REFERENCES prodotto(Codiceprodotto) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_visita_guida FOREIGN KEY (CFGuida) REFERENCES guida(CF) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS articolo_personale (
  Codiceprodotto INT PRIMARY KEY,
  CFCalciatore CHAR(16) NOT NULL,
  CONSTRAINT fk_artpers_prodotto FOREIGN KEY (Codiceprodotto) REFERENCES prodotto(Codiceprodotto) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_artpers_calciatore FOREIGN KEY (CFCalciatore) REFERENCES calciatore(CF) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS articolo_generale (
  Codiceprodotto INT PRIMARY KEY,
  CONSTRAINT fk_artgen_prodotto FOREIGN KEY (Codiceprodotto) REFERENCES prodotto(Codiceprodotto) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS sconto (
  IDSconto INT PRIMARY KEY AUTO_INCREMENT,
  IDAbbonamento INT NOT NULL,
  SogliaImporto DECIMAL(10,2) NOT NULL,
  Percentuale DECIMAL(5,2) NOT NULL
  -- FK aggiunta solo se la colonna esiste nel tuo schema
);

-- Se abbonamento esiste con chiave IDabbonamento, aggiungo la FK
SET @abbon_col := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='abbonamento' AND COLUMN_NAME IN ('IDabbonamento','IDAbbonamento'));
SET @fk_exists := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS
  WHERE CONSTRAINT_SCHEMA = DATABASE() AND CONSTRAINT_NAME='fk_sconto_abbonamento');
SET @sql := IF(@abbon_col > 0 AND @fk_exists = 0,
'ALTER TABLE sconto ADD CONSTRAINT fk_sconto_abbonamento FOREIGN KEY (IDAbbonamento) REFERENCES abbonamento(IDabbonamento) ON DELETE CASCADE ON UPDATE CASCADE',
'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

CREATE TABLE IF NOT EXISTS statistiche_individuali (
  IDStat INT PRIMARY KEY AUTO_INCREMENT,
  CF CHAR(16) NOT NULL,
  IDPartita INT NOT NULL,
  Gol INT NOT NULL DEFAULT 0,
  Assist INT NOT NULL DEFAULT 0,
  CONSTRAINT fk_stat_calciatore FOREIGN KEY (CF) REFERENCES calciatore(CF) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_stat_partita FOREIGN KEY (IDPartita) REFERENCES partita(IDPartita) ON DELETE CASCADE ON UPDATE CASCADE
);

-- 2) SEED MINIMO (idempotente)

INSERT IGNORE INTO stagione (Anno, PosizioneClassifica, EsitoCoppaNazionale, EsitoCoppaEuropea) VALUES
(2023, 3, 'Semifinale', 'Quarti'),
(2024, NULL, NULL, NULL);

INSERT INTO squadra (Nome, TrofeiVinti, Stagione)
SELECT 'Juventus', 70, 2024
WHERE NOT EXISTS (SELECT 1 FROM squadra);

INSERT INTO partita (Data, SquadraAvversaria, Competizione, Risultato, AnnoStagione, InCasa)
SELECT * FROM (
  SELECT '2024-08-25', 'Udinese',    'Serie A',     '2-0', 2024, 1
  UNION ALL SELECT '2024-09-14', 'Lazio',      'Serie A',     '1-1', 2024, 1
  UNION ALL SELECT '2024-09-21', 'Atalanta',   'Serie A',     NULL,  2024, 0
  UNION ALL SELECT '2024-10-03', 'Fiorentina', 'Coppa Italia',NULL,  2024, 0
) t
WHERE NOT EXISTS (SELECT 1 FROM partita);

INSERT IGNORE INTO ordine (Codiceordine, Data, Rimborsato, CF) VALUES
(5001, '2024-09-01', 0, 'KABKVS21H78QY2PI'),
(5002, '2024-09-14', 0, 'UTHMVQ09Z78SIDQZ'),
(5003, '2024-09-14', 0, 'MPVSED25H59H24VU'),
(5004, '2024-10-02', 0, 'YACSJS88O93JFEGJ'),
(5005, '2024-10-03', 0, 'XKAKGK56J49AP11G');

INSERT IGNORE INTO biglietto (Codiceprodotto, IDPartita)
SELECT 6001, IDPartita FROM partita WHERE Data='2024-08-25' AND SquadraAvversaria='Udinese';
INSERT IGNORE INTO biglietto (Codiceprodotto, IDPartita)
SELECT 6002, IDPartita FROM partita WHERE Data='2024-09-14' AND SquadraAvversaria='Lazio';

INSERT IGNORE INTO visita_guidata (Codiceprodotto, DataVisita, CFGuida) VALUES
(6003, '2024-10-10', 'LMBCRS85C01F205Z');

INSERT IGNORE INTO articolo_personale (Codiceprodotto, CFCalciatore) VALUES
(6004, 'RSSMRA85C01H501Z');
INSERT IGNORE INTO articolo_generale (Codiceprodotto) VALUES
(6005);

-- SEED CONDIZIONALE ABBONAMENTO (compatibile con schemi diversi)
SET @has_nome := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='abbonamento' AND COLUMN_NAME='Nome');
SET @has_anno := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='abbonamento' AND COLUMN_NAME='Anno');
SET @has_tipo := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='abbonamento' AND COLUMN_NAME='Tipodiabbonamento');
SET @has_cf := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME='abbonamento' AND COLUMN_NAME='CF');

SET @sql := IF(@has_nome > 0,
"INSERT IGNORE INTO abbonamento (IDAbbonamento, Nome, Descrizione) VALUES
(1, 'Black&White', 'Accesso priorità biglietti e 10% shop'),
(2, 'Stadium+',   'Ingresso a 10 partite in casa')",
'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

INSERT INTO cliente (CF, Nome, Cognome, Indirizzodispedizione, Mail, Password)
SELECT 'PLCHLDR000000001', 'Da', 'Completare', NULL, 'placeholder@example.com', 'temp'
WHERE @has_anno > 0 AND @has_tipo > 0 AND @has_cf > 0
  AND NOT EXISTS (SELECT 1 FROM cliente);

SET @one_cf := (SELECT CF FROM cliente ORDER BY CF LIMIT 1);

SET @sql := IF(@has_anno > 0 AND @has_tipo > 0 AND @has_cf > 0,
CONCAT("INSERT IGNORE INTO abbonamento (IDabbonamento, Anno, Tipodiabbonamento, CF) VALUES
(1, 2024, 'completo', '", @one_cf, "'),
(2, 2023, 'normale',  '", @one_cf, "')"),
'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Sconti base su abbonamenti (funziona con entrambi i modelli di abbonamento)
INSERT IGNORE INTO sconto (IDAbbonamento, SogliaImporto, Percentuale) VALUES
(1, 50.00, 10.00),
(2, 70.00, 15.00);

-- Statistiche minime
INSERT IGNORE INTO statistiche_individuali (CF, IDPartita, Gol, Assist)
SELECT 'RSSMRA85C01H501Z', IDPartita, 1, 0 FROM partita WHERE Data='2024-08-25' AND SquadraAvversaria='Udinese';
INSERT IGNORE INTO statistiche_individuali (CF, IDPartita, Gol, Assist)
SELECT 'BNCLCU90A01F205X', IDPartita, 0, 1 FROM partita WHERE Data='2024-08-25' AND SquadraAvversaria='Udinese';
INSERT IGNORE INTO statistiche_individuali (CF, IDPartita, Gol, Assist)
SELECT 'VRDLNZ92H10C351K', IDPartita, 1, 0 FROM partita WHERE Data='2024-09-14' AND SquadraAvversaria='Lazio';



/* =======================================================
   PATCH: aggiunge 'Codiceordine' a biglietto, articolo_personale,
          articolo_generale, visita_guidata. Crea FK verso ordine.
          Inserisce tuple con Codiceprodotto e Codiceordine esistenti.
          Idempotente su MySQL 5.7+.
   ======================================================= */
USE `juventusfc`;

-- ---------- BIGLIETTO ----------
-- Aggiungi colonna se manca
SET @col := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='biglietto' AND COLUMN_NAME='Codiceordine');
SET @sql := IF(@col=0, 'ALTER TABLE biglietto ADD COLUMN Codiceordine INT NULL', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
-- Indice
SET @idx := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.STATISTICS
  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='biglietto' AND INDEX_NAME='idx_biglietto_codiceordine');
SET @sql := IF(@idx=0, 'ALTER TABLE biglietto ADD INDEX idx_biglietto_codiceordine (Codiceordine)', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
-- FK se manca
SET @fk := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS
  WHERE CONSTRAINT_SCHEMA=DATABASE() AND CONSTRAINT_NAME='fk_biglietto_ordine');
SET @sql := IF(@fk=0,
  'ALTER TABLE biglietto ADD CONSTRAINT fk_biglietto_ordine FOREIGN KEY (Codiceordine) REFERENCES ordine(Codiceordine) ON DELETE RESTRICT ON UPDATE CASCADE',
  'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Seed: prodotti 1..2, ordini 1..2, partite note
INSERT IGNORE INTO biglietto (Codiceprodotto, IDPartita, Codiceordine)
SELECT 1, p.IDPartita, 1 FROM partita p WHERE p.Data='2024-08-25' LIMIT 1;
INSERT IGNORE INTO biglietto (Codiceprodotto, IDPartita, Codiceordine)
SELECT 2, p.IDPartita, 2 FROM partita p WHERE p.Data='2024-09-14' LIMIT 1;

-- ---------- ARTICOLO_PERSONALE ----------
SET @col := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='articolo_personale' AND COLUMN_NAME='Codiceordine');
SET @sql := IF(@col=0, 'ALTER TABLE articolo_personale ADD COLUMN Codiceordine INT NULL', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @idx := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.STATISTICS
  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='articolo_personale' AND INDEX_NAME='idx_artpers_codiceordine');
SET @sql := IF(@idx=0, 'ALTER TABLE articolo_personale ADD INDEX idx_artpers_codiceordine (Codiceordine)', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @fk := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS
  WHERE CONSTRAINT_SCHEMA=DATABASE() AND CONSTRAINT_NAME='fk_artpers_ordine');
SET @sql := IF(@fk=0,
  'ALTER TABLE articolo_personale ADD CONSTRAINT fk_artpers_ordine FOREIGN KEY (Codiceordine) REFERENCES ordine(Codiceordine) ON DELETE RESTRICT ON UPDATE CASCADE',
  'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Seed: prodotti 3..4, ordini 3..4, CF calciatori reali
INSERT IGNORE INTO articolo_personale (Codiceprodotto, CFCalciatore, Codiceordine)
VALUES
(3, 'RSSMRA85C01H501Z', 3),
(4, 'BNCLCU90A01F205X', 4);

-- ---------- ARTICOLO_GENERALE ----------
SET @col := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='articolo_generale' AND COLUMN_NAME='Codiceordine');
SET @sql := IF(@col=0, 'ALTER TABLE articolo_generale ADD COLUMN Codiceordine INT NULL', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @idx := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.STATISTICS
  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='articolo_generale' AND INDEX_NAME='idx_artgen_codiceordine');
SET @sql := IF(@idx=0, 'ALTER TABLE articolo_generale ADD INDEX idx_artgen_codiceordine (Codiceordine)', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @fk := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS
  WHERE CONSTRAINT_SCHEMA=DATABASE() AND CONSTRAINT_NAME='fk_artgen_ordine');
SET @sql := IF(@fk=0,
  'ALTER TABLE articolo_generale ADD CONSTRAINT fk_artgen_ordine FOREIGN KEY (Codiceordine) REFERENCES ordine(Codiceordine) ON DELETE RESTRICT ON UPDATE CASCADE',
  'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Seed: prodotti 5..6, ordini 5..6
INSERT IGNORE INTO articolo_generale (Codiceprodotto, Codiceordine) VALUES
(5, 5),
(6, 6);

-- ---------- VISITA_GUIDATA ----------
SET @col := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='visita_guidata' AND COLUMN_NAME='Codiceordine');
SET @sql := IF(@col=0, 'ALTER TABLE visita_guidata ADD COLUMN Codiceordine INT NULL', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @idx := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.STATISTICS
  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='visita_guidata' AND INDEX_NAME='idx_visita_codiceordine');
SET @sql := IF(@idx=0, 'ALTER TABLE visita_guidata ADD INDEX idx_visita_codiceordine (Codiceordine)', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @fk := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS
  WHERE CONSTRAINT_SCHEMA=DATABASE() AND CONSTRAINT_NAME='fk_visita_ordine2');
SET @sql := IF(@fk=0,
  'ALTER TABLE visita_guidata ADD CONSTRAINT fk_visita_ordine2 FOREIGN KEY (Codiceordine) REFERENCES ordine(Codiceordine) ON DELETE RESTRICT ON UPDATE CASCADE',
  'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Seed: prodotti 7..8, ordini 7..8, guide reali
INSERT IGNORE INTO visita_guidata (Codiceprodotto, DataVisita, CFGuida, Codiceordine) VALUES
(7, '2024-10-10', 'LMBCRS85C01F205Z', 7),
(8, '2024-11-05', 'NGRFNC88B12G702Q', 8);

-- Rendi NOT NULL dopo il popolamento (se tutte le righe hanno valore)
SET @missing := (SELECT COUNT(*) FROM biglietto WHERE Codiceordine IS NULL);
SET @sql := IF(@missing=0, 'ALTER TABLE biglietto MODIFY Codiceordine INT NOT NULL', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @missing := (SELECT COUNT(*) FROM articolo_personale WHERE Codiceordine IS NULL);
SET @sql := IF(@missing=0, 'ALTER TABLE articolo_personale MODIFY Codiceordine INT NOT NULL', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @missing := (SELECT COUNT(*) FROM articolo_generale WHERE Codiceordine IS NULL);
SET @sql := IF(@missing=0, 'ALTER TABLE articolo_generale MODIFY Codiceordine INT NOT NULL', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @missing := (SELECT COUNT(*) FROM visita_guidata WHERE Codiceordine IS NULL);
SET @sql := IF(@missing=0, 'ALTER TABLE visita_guidata MODIFY Codiceordine INT NOT NULL', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;



/* ======================================================================
   FINAL PATCH (APPLICATO DOPO IL FILE ALLEGATO) - MySQL 5.7/8.0
   Soddisfa i requisiti:
   - PRODOTTO: rimuovi Codiceordine, aggiungi Tipologia ENUM('Articolopersonale','Articologenerale','Biglietto','Visitaguidata'),
               limita a MAX 60 tuple (Codiceprodotto più piccoli), popola Tipologia in modo ciclico se NULL.
   - BIGLIETTO, ARTICOLO_PERSONALE, ARTICOLO_GENERALE, VISITA_GUIDATA:
       * NESSUN ID proprio -> PK = Codiceprodotto
       * hanno Codiceordine (FK a ordine)
       * accettano solo Codiceprodotto coerenti con Tipologia (trigger)
       * pulizia dati non coerenti, dedup per Codiceprodotto, seed minimo se vuote
   ====================================================================== */

-- Assicura lo schema corretto
USE `juventusfc`;

-- ==================== PRODOTTO ====================



-- ==================== Helper: tabella temporanea per dedup per Codiceprodotto ====================
DROP TEMPORARY TABLE IF EXISTS tmp_keep;
CREATE TEMPORARY TABLE tmp_keep (
  tname VARCHAR(64) NOT NULL,
  Codiceprodotto INT NOT NULL,
  Codiceordine INT NULL
);


/* ==================== BIGLIETTO ==================== */
-- Aggiungi Codiceordine se manca
SET @col := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='biglietto' AND COLUMN_NAME='Codiceordine');
SET @sql := IF(@col=0, 'ALTER TABLE biglietto ADD COLUMN Codiceordine INT NULL', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Drop ID se presente
SET @has_id := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='biglietto' AND COLUMN_NAME IN ('IDBiglietto','id_biglietto'));
SET @sql := IF(@has_id>0, 'ALTER TABLE biglietto DROP COLUMN IDBiglietto', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Pulisci riferimenti a prodotti non esistenti o tipologia non coerente
DELETE b FROM biglietto b LEFT JOIN prodotto p ON p.Codiceprodotto=b.Codiceprodotto WHERE p.Codiceprodotto IS NULL;
DELETE b FROM biglietto b JOIN prodotto p ON p.Codiceprodotto=b.Codiceprodotto WHERE p.Tipologia <> 'Biglietto';

-- Deduplica: tieni per ciascun Codiceprodotto il più piccolo Codiceordine
TRUNCATE tmp_keep;
INSERT INTO tmp_keep(tname,Codiceprodotto,Codiceordine)
SELECT 'biglietto', Codiceprodotto, MIN(Codiceordine) FROM biglietto GROUP BY Codiceprodotto;
DELETE b FROM biglietto b
LEFT JOIN tmp_keep k ON k.tname='biglietto' AND k.Codiceprodotto=b.Codiceprodotto AND (k.Codiceordine<=>b.Codiceordine)
WHERE k.Codiceprodotto IS NULL;

-- Se vuota, seed con prime 10 coppie coerenti (prodotti 'Biglietto' e ordini 1..10)
SET @rb := 0; SET @ro := 0;
SET @rp := 0; SET @np := (SELECT COUNT(*) FROM partita);
  INSERT INTO biglietto (Codiceprodotto, Codiceordine, IDPartita)
  SELECT b.Codiceprodotto, o.Codiceordine, p.IDPartita
  FROM (SELECT Codiceprodotto, (@rb:=@rb+1) AS rn
        FROM prodotto
        WHERE Tipologia='Biglietto'
        ORDER BY Codiceprodotto
        LIMIT 10) b
  JOIN (SELECT Codiceordine, (@ro:=@ro+1) AS rn
        FROM ordine
        ORDER BY Codiceordine
        LIMIT 10) o
    ON b.rn = o.rn
  JOIN (SELECT IDPartita, (@rp:=@rp+1) AS rn
        FROM partita
        ORDER BY IDPartita) p
    ON p.rn = ((b.rn - 1) % GREATEST(@np,1)) + 1
WHERE NOT EXISTS (SELECT 1 FROM biglietto);



-- Imposta PK su Codiceprodotto
SET @has_pk := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='biglietto' AND CONSTRAINT_TYPE='PRIMARY KEY');
SET @sql := IF(@has_pk>0, 'ALTER TABLE biglietto DROP PRIMARY KEY, ADD PRIMARY KEY (Codiceprodotto)',
                         'ALTER TABLE biglietto ADD PRIMARY KEY (Codiceprodotto)');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Aggiungi FKs
SET @fk1 := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS
  WHERE CONSTRAINT_SCHEMA=DATABASE() AND CONSTRAINT_NAME='fk_biglietto_prodotto');
SET @sql := IF(@fk1=0, 'ALTER TABLE biglietto ADD CONSTRAINT fk_biglietto_prodotto FOREIGN KEY (Codiceprodotto) REFERENCES prodotto(Codiceprodotto) ON UPDATE CASCADE ON DELETE RESTRICT', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
SET @fk2 := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS
  WHERE CONSTRAINT_SCHEMA=DATABASE() AND CONSTRAINT_NAME='fk_biglietto_ordine');
SET @sql := IF(@fk2=0, 'ALTER TABLE biglietto ADD CONSTRAINT fk_biglietto_ordine FOREIGN KEY (Codiceordine) REFERENCES ordine(Codiceordine) ON UPDATE CASCADE ON DELETE RESTRICT', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- Trigger coerenza tipologia
DROP TRIGGER IF EXISTS bi_biglietto_tipologia;
DELIMITER //
CREATE TRIGGER bi_biglietto_tipologia BEFORE INSERT ON biglietto
FOR EACH ROW
BEGIN
  DECLARE t ENUM('Articolopersonale','Articologenerale','Biglietto','Visitaguidata');
  SELECT Tipologia INTO t FROM prodotto WHERE Codiceprodotto=NEW.Codiceprodotto;
  IF t <> 'Biglietto' THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Tipologia prodotto non coerente per BIGLIETTO';
  END IF;
END//
DELIMITER ;
DROP TRIGGER IF EXISTS bu_biglietto_tipologia;
DELIMITER //
CREATE TRIGGER bu_biglietto_tipologia BEFORE UPDATE ON biglietto
FOR EACH ROW
BEGIN
  DECLARE t ENUM('Articolopersonale','Articologenerale','Biglietto','Visitaguidata');
  SELECT Tipologia INTO t FROM prodotto WHERE Codiceprodotto=NEW.Codiceprodotto;
  IF t <> 'Biglietto' THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Tipologia prodotto non coerente per BIGLIETTO';
  END IF;
END//
DELIMITER ;


/* ==================== ARTICOLO_PERSONALE ==================== */
SET @col := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='articolo_personale' AND COLUMN_NAME='Codiceordine');
SET @sql := IF(@col=0, 'ALTER TABLE articolo_personale ADD COLUMN Codiceordine INT NULL', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @has_id := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='articolo_personale' AND COLUMN_NAME IN ('IDArticoloPersonale','id_articolopersonale'));
SET @sql := IF(@has_id>0, 'ALTER TABLE articolo_personale DROP COLUMN IDArticoloPersonale', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

DELETE ap FROM articolo_personale ap LEFT JOIN prodotto p ON p.Codiceprodotto=ap.Codiceprodotto WHERE p.Codiceprodotto IS NULL;
DELETE ap FROM articolo_personale ap JOIN prodotto p ON p.Codiceprodotto=ap.Codiceprodotto WHERE p.Tipologia <> 'Articolopersonale';

TRUNCATE tmp_keep;
INSERT INTO tmp_keep(tname,Codiceprodotto,Codiceordine)
SELECT 'articolo_personale', Codiceprodotto, MIN(Codiceordine) FROM articolo_personale GROUP BY Codiceprodotto;
DELETE ap FROM articolo_personale ap
LEFT JOIN tmp_keep k ON k.tname='articolo_personale' AND k.Codiceprodotto=ap.Codiceprodotto AND (k.Codiceordine<=>ap.Codiceordine)
WHERE k.Codiceprodotto IS NULL;

-- Seed se vuota
SET @r1 := 0; SET @o1 := 0; SET @rc := 0; SET @nc := (SELECT COUNT(*) FROM calciatore);
  INSERT INTO articolo_personale (Codiceprodotto, CFCalciatore, Codiceordine)
  SELECT p.Codiceprodotto, c.CF, o.Codiceordine
  FROM (SELECT Codiceprodotto, (@r1:=@r1+1) rn FROM prodotto WHERE Tipologia='Articolopersonale' ORDER BY Codiceprodotto LIMIT 10) p
  JOIN (SELECT Codiceordine, (@o1:=@o1+1) rn FROM ordine ORDER BY Codiceordine LIMIT 10 OFFSET 10) o
    ON p.rn = o.rn
  JOIN (SELECT CF, (@rc:=@rc+1) rn FROM calciatore ORDER BY CF) c
    ON c.rn = ((p.rn - 1) % GREATEST(@nc,1)) + 1
WHERE NOT EXISTS (SELECT 1 FROM articolo_personale);



SET @has_pk := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='articolo_personale' AND CONSTRAINT_TYPE='PRIMARY KEY');
SET @sql := IF(@has_pk>0, 'ALTER TABLE articolo_personale DROP PRIMARY KEY, ADD PRIMARY KEY (Codiceprodotto)',
                         'ALTER TABLE articolo_personale ADD PRIMARY KEY (Codiceprodotto)');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @fk1 := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS
  WHERE CONSTRAINT_SCHEMA=DATABASE() AND CONSTRAINT_NAME='fk_artpers_prodotto');
SET @sql := IF(@fk1=0, 'ALTER TABLE articolo_personale ADD CONSTRAINT fk_artpers_prodotto FOREIGN KEY (Codiceprodotto) REFERENCES prodotto(Codiceprodotto) ON UPDATE CASCADE ON DELETE RESTRICT', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
SET @fk2 := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS
  WHERE CONSTRAINT_SCHEMA=DATABASE() AND CONSTRAINT_NAME='fk_artpers_ordine');
SET @sql := IF(@fk2=0, 'ALTER TABLE articolo_personale ADD CONSTRAINT fk_artpers_ordine FOREIGN KEY (Codiceordine) REFERENCES ordine(Codiceordine) ON UPDATE CASCADE ON DELETE RESTRICT', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

DROP TRIGGER IF EXISTS bi_artpers_tipologia;
DELIMITER //
CREATE TRIGGER bi_artpers_tipologia BEFORE INSERT ON articolo_personale
FOR EACH ROW
BEGIN
  DECLARE t ENUM('Articolopersonale','Articologenerale','Biglietto','Visitaguidata');
  SELECT Tipologia INTO t FROM prodotto WHERE Codiceprodotto=NEW.Codiceprodotto;
  IF t <> 'Articolopersonale' THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Tipologia prodotto non coerente per ARTICOLO_PERSONALE';
  END IF;
END//
DELIMITER ;
DROP TRIGGER IF EXISTS bu_artpers_tipologia;
DELIMITER //
CREATE TRIGGER bu_artpers_tipologia BEFORE UPDATE ON articolo_personale
FOR EACH ROW
BEGIN
  DECLARE t ENUM('Articolopersonale','Articologenerale','Biglietto','Visitaguidata');
  SELECT Tipologia INTO t FROM prodotto WHERE Codiceprodotto=NEW.Codiceprodotto;
  IF t <> 'Articolopersonale' THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Tipologia prodotto non coerente per ARTICOLO_PERSONALE';
  END IF;
END//
DELIMITER ;


/* ==================== ARTICOLO_GENERALE ==================== */
SET @col := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='articolo_generale' AND COLUMN_NAME='Codiceordine');
SET @sql := IF(@col=0, 'ALTER TABLE articolo_generale ADD COLUMN Codiceordine INT NULL', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @has_id := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='articolo_generale' AND COLUMN_NAME IN ('IDArticoloGenerale','id_articologenerale'));
SET @sql := IF(@has_id>0, 'ALTER TABLE articolo_generale DROP COLUMN IDArticoloGenerale', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

DELETE ag FROM articolo_generale ag LEFT JOIN prodotto p ON p.Codiceprodotto=ag.Codiceprodotto WHERE p.Codiceprodotto IS NULL;
DELETE ag FROM articolo_generale ag JOIN prodotto p ON p.Codiceprodotto=ag.Codiceprodotto WHERE p.Tipologia <> 'Articologenerale';

TRUNCATE tmp_keep;
INSERT INTO tmp_keep(tname,Codiceprodotto,Codiceordine)
SELECT 'articolo_generale', Codiceprodotto, MIN(Codiceordine) FROM articolo_generale GROUP BY Codiceprodotto;
DELETE ag FROM articolo_generale ag
LEFT JOIN tmp_keep k ON k.tname='articolo_generale' AND k.Codiceprodotto=ag.Codiceprodotto AND (k.Codiceordine<=>ag.Codiceordine)
WHERE k.Codiceprodotto IS NULL;

-- Seed se vuota
SET @r2 := 0; SET @o2 := 0;
  INSERT INTO articolo_generale (Codiceprodotto, Codiceordine)
  SELECT p.Codiceprodotto, o.Codiceordine
  FROM (SELECT Codiceprodotto, (@r2:=@r2+1) rn FROM prodotto WHERE Tipologia='Articologenerale' ORDER BY Codiceprodotto LIMIT 10 OFFSET 10) p
  JOIN (SELECT Codiceordine, (@o2:=@o2+1) rn FROM ordine ORDER BY Codiceordine LIMIT 10 OFFSET 20) o
    ON p.rn = o.rn
WHERE NOT EXISTS (SELECT 1 FROM articolo_generale);



SET @has_pk := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='articolo_generale' AND CONSTRAINT_TYPE='PRIMARY KEY');
SET @sql := IF(@has_pk>0, 'ALTER TABLE articolo_generale DROP PRIMARY KEY, ADD PRIMARY KEY (Codiceprodotto)',
                         'ALTER TABLE articolo_generale ADD PRIMARY KEY (Codiceprodotto)');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @fk1 := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS
  WHERE CONSTRAINT_SCHEMA=DATABASE() AND CONSTRAINT_NAME='fk_artgen_prodotto');
SET @sql := IF(@fk1=0, 'ALTER TABLE articolo_generale ADD CONSTRAINT fk_artgen_prodotto FOREIGN KEY (Codiceprodotto) REFERENCES prodotto(Codiceprodotto) ON UPDATE CASCADE ON DELETE RESTRICT', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
SET @fk2 := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS
  WHERE CONSTRAINT_SCHEMA=DATABASE() AND CONSTRAINT_NAME='fk_artgen_ordine');
SET @sql := IF(@fk2=0, 'ALTER TABLE articolo_generale ADD CONSTRAINT fk_artgen_ordine FOREIGN KEY (Codiceordine) REFERENCES ordine(Codiceordine) ON UPDATE CASCADE ON DELETE RESTRICT', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

DROP TRIGGER IF EXISTS bi_artgen_tipologia;
DELIMITER //
CREATE TRIGGER bi_artgen_tipologia BEFORE INSERT ON articolo_generale
FOR EACH ROW
BEGIN
  DECLARE t ENUM('Articolopersonale','Articologenerale','Biglietto','Visitaguidata');
  SELECT Tipologia INTO t FROM prodotto WHERE Codiceprodotto=NEW.Codiceprodotto;
  IF t <> 'Articologenerale' THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Tipologia prodotto non coerente per ARTICOLO_GENERALE';
  END IF;
END//
DELIMITER ;
DROP TRIGGER IF EXISTS bu_artgen_tipologia;
DELIMITER //
CREATE TRIGGER bu_artgen_tipologia BEFORE UPDATE ON articolo_generale
FOR EACH ROW
BEGIN
  DECLARE t ENUM('Articolopersonale','Articologenerale','Biglietto','Visitaguidata');
  SELECT Tipologia INTO t FROM prodotto WHERE Codiceprodotto=NEW.Codiceprodotto;
  IF t <> 'Articologenerale' THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Tipologia prodotto non coerente per ARTICOLO_GENERALE';
  END IF;
END//
DELIMITER ;


/* ==================== VISITA_GUIDATA ==================== */
SET @col := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='visita_guidata' AND COLUMN_NAME='Codiceordine');
SET @sql := IF(@col=0, 'ALTER TABLE visita_guidata ADD COLUMN Codiceordine INT NULL', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @has_id := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='visita_guidata' AND COLUMN_NAME IN ('IDVisita','id_visita'));
SET @sql := IF(@has_id>0, 'ALTER TABLE visita_guidata DROP COLUMN IDVisita', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

DELETE vg FROM visita_guidata vg LEFT JOIN prodotto p ON p.Codiceprodotto=vg.Codiceprodotto WHERE p.Codiceprodotto IS NULL;
DELETE vg FROM visita_guidata vg JOIN prodotto p ON p.Codiceprodotto=vg.Codiceprodotto WHERE p.Tipologia <> 'Visitaguidata';

TRUNCATE tmp_keep;
INSERT INTO tmp_keep(tname,Codiceprodotto,Codiceordine)
SELECT 'visita_guidata', Codiceprodotto, MIN(Codiceordine) FROM visita_guidata GROUP BY Codiceprodotto;
DELETE vg FROM visita_guidata vg
LEFT JOIN tmp_keep k ON k.tname='visita_guidata' AND k.Codiceprodotto=vg.Codiceprodotto AND (k.Codiceordine<=>vg.Codiceordine)
WHERE k.Codiceprodotto IS NULL;

-- Seed se vuota
SET @r3 := 0; SET @o3 := 0;
  INSERT INTO visita_guidata (Codiceprodotto, Codiceordine, DataVisita)
  SELECT p.Codiceprodotto, o.Codiceordine, DATE_ADD('2025-01-01', INTERVAL p.rn DAY)
  FROM (SELECT Codiceprodotto, (@r3:=@r3+1) rn FROM prodotto WHERE Tipologia='Visitaguidata' ORDER BY Codiceprodotto LIMIT 10) p
  JOIN (SELECT Codiceordine, (@o3:=@o3+1) rn FROM ordine ORDER BY Codiceordine LIMIT 10 OFFSET 30) o
    ON p.rn = o.rn
WHERE NOT EXISTS (SELECT 1 FROM visita_guidata);



SET @has_pk := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS
  WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='visita_guidata' AND CONSTRAINT_TYPE='PRIMARY KEY');
SET @sql := IF(@has_pk>0, 'ALTER TABLE visita_guidata DROP PRIMARY KEY, ADD PRIMARY KEY (Codiceprodotto)',
                         'ALTER TABLE visita_guidata ADD PRIMARY KEY (Codiceprodotto)');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @fk1 := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS
  WHERE CONSTRAINT_SCHEMA=DATABASE() AND CONSTRAINT_NAME='fk_visita_prodotto');
SET @sql := IF(@fk1=0, 'ALTER TABLE visita_guidata ADD CONSTRAINT fk_visita_prodotto FOREIGN KEY (Codiceprodotto) REFERENCES prodotto(Codiceprodotto) ON UPDATE CASCADE ON DELETE RESTRICT', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
SET @fk2 := (SELECT COUNT(*) FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS
  WHERE CONSTRAINT_SCHEMA=DATABASE() AND CONSTRAINT_NAME='fk_visita_ordine');
SET @sql := IF(@fk2=0, 'ALTER TABLE visita_guidata ADD CONSTRAINT fk_visita_ordine FOREIGN KEY (Codiceordine) REFERENCES ordine(Codiceordine) ON UPDATE CASCADE ON DELETE RESTRICT', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

DROP TRIGGER IF EXISTS bi_visita_tipologia;
DELIMITER //
CREATE TRIGGER bi_visita_tipologia BEFORE INSERT ON visita_guidata
FOR EACH ROW
BEGIN
  DECLARE t ENUM('Articolopersonale','Articologenerale','Biglietto','Visitaguidata');
  SELECT Tipologia INTO t FROM prodotto WHERE Codiceprodotto=NEW.Codiceprodotto;
  IF t <> 'Visitaguidata' THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Tipologia prodotto non coerente per VISITA_GUIDATA';
  END IF;
END//
DELIMITER ;
DROP TRIGGER IF EXISTS bu_visita_tipologia;
DELIMITER //
CREATE TRIGGER bu_visita_tipologia BEFORE UPDATE ON visita_guidata
FOR EACH ROW
BEGIN
  DECLARE t ENUM('Articolopersonale','Articologenerale','Biglietto','Visitaguidata');
  SELECT Tipologia INTO t FROM prodotto WHERE Codiceprodotto=NEW.Codiceprodotto;
  IF t <> 'Visitaguidata' THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT='Tipologia prodotto non coerente per VISITA_GUIDATA';
  END IF;
END//
DELIMITER ;

-- Pulizia helper
DROP TEMPORARY TABLE IF EXISTS tmp_keep;
