package org.smgame.backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.smgame.core.card.Card;
import org.smgame.util.Logging;

/** Classe DBTransactions/gestisce le transazioni col database
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class DBTransactions {

    private final String tableTrans = "TRANSACTION"; //nome tabella
    private final String[] columnTrans = new String[]{"transaction_id", "game_id", "manche",
                                                      "player_name", "score", "win_lose_amount"};
    private final String tableCard = "CARD c";
    private final String[] columnCard = new String[]{"c.card_id", "c.suit", "c.point"};
    private final String tableRelation = "TRANSACTION_CARD";
    private final String[] columnRelation = new String[]{"transaction_id", "card_id"};
    private long idT; //id transazione
    private long id_game; //id partita
    private int manche; //numero manche
    private String player; //nome giocatore
    private double score; //punteggio
    private double win; //vincita
    private ArrayList<Card> cardAL;
    private ArrayList<DBTransactions> transactionsAL;

    /**Costruttore vuoto
     *
     */
    public DBTransactions() {}

    /**Costruttore
     * 
     * @param id_game id partita
     */
    public DBTransactions(long id_game) {
        this.id_game = id_game;
    }

    /**Costruttore
     *
     * @param id_game id partita
     * @param manche numero di manche nel gioco
     * @param player giocatore
     * @param score punteggio
     * @param win vincita
     */
    public DBTransactions(long id_game, int manche, String player, double score,
            double win) {
        this.id_game = id_game;
        this.manche = manche;
        this.player = player;
        this.score = score;
        this.win = win;
    }

    /**Costruttore
     *
     * @param id_game id partita
     * @param manche numero di manche nel gioco
     * @param player giocatore
     * @param score punteggio
     * @param win vincita
     * @param cardal arraylist di carte
     */
    public DBTransactions(long id_game, int manche, String player, double score,
            double win, List<Card> cardal) {
        this.id_game = id_game;
        this.manche = manche;
        this.player = player;
        this.score = score;
        this.win = win;
        this.cardAL = (ArrayList<Card>) cardal;
    }

    /**Restituisce id gioco
     *
     * @return idgioco
     */
    public long getId_game() {
        return id_game;
    }

    /**Restituisce nome giocatore
     *
     * @return nome
     */
    public String getPlayer() {
        return player;
    }

    /**Restituisce punteggio
     *
     * @return punteggio
     */
    public double getScore() {
        return score;
    }

    /**restituisce vincita/perdita
     *
     * @return vincita o pertita
     */
    public double getWin() {
        return win;
    }

    /**Restituisce la manche
     *
     * @return numero di manche
     */
    public int getManche() {
        return manche;
    }

    /**imposta l'id della transazione
     *
     * @param id numero transazione
     */
    public void setIdTransaction(long id) {
        this.idT = id;
    }

    /**Restituisce l'id transazione
     *
     * @return id transazione
     */
    public long getIdTransaction() {
        return idT;
    }

    /**registra su db la singola transazione
     *
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     * @throws java.lang.Exception
     */
    public void executeSingleTransaction() throws ClassNotFoundException, SQLException,
            IOException, Exception {
        Connection conn = DBAccess.getConnection();
        String sql = "INSERT INTO " + tableTrans + "(" + columnTrans[1] + "," + columnTrans[2] + "," +
                columnTrans[3] + "," + columnTrans[4] + "," + columnTrans[5] + ") VALUES (?,?,?,?,?)";
        PreparedStatement prpstmt = conn.prepareStatement(sql);
        setParameter(prpstmt, 1, getId_game(), Types.BIGINT);
        setParameter(prpstmt, 2, getManche(), Types.INTEGER);
        setParameter(prpstmt, 3, getPlayer(), Types.VARCHAR);
        setParameter(prpstmt, 4, getScore(), Types.DOUBLE);
        setParameter(prpstmt, 5, getWin(), Types.DOUBLE);
        Logging.logInfo(prpstmt.toString());
        prpstmt.execute();
        setIdTransaction(getLastInsertId(conn));
        executeArraylistCardTransaction(conn);
    }

    /**registra su db tutto l'arraylist transazioni
     * 
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     * @throws java.lang.Exception
     */
    public void executeArraylistTransactions() throws ClassNotFoundException, SQLException,
            IOException, Exception {
        for (int i = 0; i < transactionsAL.size(); i++) {
            transactionsAL.get(i).executeSingleTransaction();
        }
        resetArraylistTansactions();
    }

    /**Scrive su db le carte presenti nell'arraylist delle transazioni
     *
     * @param conn connessione db
     * @throws java.sql.SQLException
     * @throws java.lang.Exception
     */
    private void executeArraylistCardTransaction(Connection conn) throws SQLException, Exception {
        String sql = "INSERT INTO " + tableRelation + " VALUES (?,(SELECT " + columnCard[0] +
                " FROM " + tableCard + " WHERE " + columnCard[1] + "=? AND " + columnCard[2] + "=?));";
        for (int i = 0; i < cardAL.size(); i++) {
            PreparedStatement prpstmt = conn.prepareStatement(sql);
            setParameter(prpstmt, 1, getIdTransaction(), Types.BIGINT);
            setParameter(prpstmt, 2, cardAL.get(i).getSuit().toString(), Types.VARCHAR);
            setParameter(prpstmt, 3, cardAL.get(i).getPoint().toString(), Types.VARCHAR);
            Logging.logInfo(prpstmt.toString());
            prpstmt.execute();
        }
    }

    /**Aggiunge all'arraylist una transazione da registrare a posteriori
     *
     * @param dbt transazione
     */
    public void addToArraylistTransactions(DBTransactions dbt) {
        if (transactionsAL == null) {
            transactionsAL = new ArrayList<DBTransactions>();
        }
        transactionsAL.add(dbt);
    }

    /**azzera/resetta l'arraylist
     *
     */
    public void resetArraylistTansactions() {
        transactionsAL = null;
    }

    /**Restituisce l'arraylist delle transazioni
     *
     * @return arraylist transazioni
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     * @throws java.lang.Exception
     */
    public ArrayList<DBTransactions> getTransactionsAL() throws
            ClassNotFoundException, SQLException, IOException, Exception {

        ArrayList<DBTransactions> dbtransactionsAL = new ArrayList<DBTransactions>();
        Connection conn = DBAccess.getConnection();
        String sql = "SELECT * FROM " + tableTrans + " WHERE " + columnTrans[1] + "= ?;";
        PreparedStatement prpstmt = conn.prepareStatement(sql);
        setParameter(prpstmt, 1, getId_game(), Types.BIGINT);
        Logging.logInfo(prpstmt.toString());
        ResultSet rs = prpstmt.executeQuery();
        while (rs.next()) {
            DBTransactions dbt = new DBTransactions(id_game, rs.getInt(2), rs.getString(3),
                    rs.getDouble(4), rs.getDouble(5));
            dbtransactionsAL.add(dbt);
        }
        return dbtransactionsAL;
    }

    /**Cerca l'ultimo id appena inserito della transazione
     *
     */
    private long getLastInsertId(Connection conn) throws ClassNotFoundException, SQLException, IOException {
        long last_id = -1;
        String sql = "SELECT LAST_INSERT_ID();";
        PreparedStatement prpstmt = conn.prepareStatement(sql);
        ResultSet rs = prpstmt.executeQuery();
        if (rs.next()) {
            last_id = rs.getLong(1);
        }
        return last_id;
    }

    /**Restituisce un map (long, matrice oggetti) ordinato per inserimento
     *
     * @return oggetto maps
     * 
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     * @throws java.lang.Exception
     */
    public LinkedHashMap<Long, Object[][]> getStoryGame() throws
            ClassNotFoundException, SQLException, IOException, Exception {

        LinkedHashMap<Long, Object[][]> map = new LinkedHashMap<Long, Object[][]>();

        String sqlDistinct = "SELECT DISTINCT(" + columnTrans[1] + ") FROM " + tableTrans;
        String sqlCount = "SELECT count(*) FROM " + tableTrans + " WHERE " + columnTrans[1] + "= ?;";
        String sqlSelect = "SELECT t." + columnTrans[2] + ", t." + columnTrans[3] + ", t." + columnTrans[4] + ", t."
                + columnTrans[5] + ", GROUP_CONCAT(LOWER("+ columnCard[2] +"), \' \', LEFT(" + columnCard[1] +",1)) AS group_card" +
                " FROM " + tableTrans + " t, " + tableCard + ", " + tableRelation +
                " r WHERE t." + columnTrans[1] + "= ? AND " + columnCard[0] + "=r." + columnRelation[1] +
                " AND r." + columnRelation[0] + "=t." + columnTrans[0] + " GROUP BY t." + columnTrans[0] +
                " ORDER BY t." + columnTrans[2] + " ASC, t." + columnTrans[5] + " DESC;";

        Connection conn = DBAccess.getConnection();
        PreparedStatement prpstmtDistinct = conn.prepareStatement(sqlDistinct);
        Logging.logInfo(prpstmtDistinct.toString());
        ResultSet rsDistinct = prpstmtDistinct.executeQuery();

        while (rsDistinct.next()) {
            Object[][] matrix = null;
            long id = rsDistinct.getLong(1);
            PreparedStatement prpstmtCount = conn.prepareStatement(sqlCount);
            setParameter(prpstmtCount, 1, id, Types.BIGINT);
            Logging.logInfo(prpstmtCount.toString());
            ResultSet rsCount = prpstmtCount.executeQuery();
            rsCount.next();
            int rows = rsCount.getInt(1);
            if (rows > 0) {
                matrix = new Object[rows][5];
                int r = 0;
                PreparedStatement prpstmtSelect = conn.prepareStatement(sqlSelect);
                setParameter(prpstmtSelect, 1, id, Types.BIGINT);
                Logging.logInfo(prpstmtSelect.toString());
                ResultSet rsSelect = prpstmtSelect.executeQuery();
                while (rsSelect.next()) {
                    matrix[r][0] = rsSelect.getInt(1);
                    matrix[r][1] = rsSelect.getString(2);
                    matrix[r][2] = rsSelect.getDouble(3);
                    matrix[r][3] = rsSelect.getDouble(4);
                    matrix[r][4] = rsSelect.getString(5);
                    r++;
                }
            } //end if
            map.put(new Long(id), matrix);
        }//end while rsDistinct
        return map;
    }


    /**imposta i tipi di valore da usare per la preparedStatement
     *
     * @param stmt preparedstatement
     * @param index indice
     * @param value valore
     * @param type tipo
     * @throws java.sql.SQLException
     * @throws java.lang.Exception
     */
    private void setParameter(PreparedStatement stmt, int index,
            Object value, int type) throws SQLException, Exception {
        if (value == null) {
            stmt.setNull(index, type);
        } else {
            if (type == Types.VARCHAR) {
                stmt.setString(index, (String) value);
            } else if (type == Types.INTEGER) {
                stmt.setInt(index, ((Integer) value).intValue());
            } else if (type == Types.BIGINT) {
                stmt.setLong(index, ((Long) value).longValue());
            } else if (type == Types.DOUBLE) {
                stmt.setDouble(index, ((Double) value).doubleValue());
            } else {
                throw new Exception("Tipo di dato non gestito");
            }
        } //end
    } //end setParameter
} //end class