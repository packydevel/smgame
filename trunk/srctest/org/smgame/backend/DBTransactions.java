package org.smgame.backend;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;

import org.smgame.core.card.Card;

import org.smgame.util.Common;
import org.smgame.util.Logging;

/** Classe DBTransactions/gestisce le transazioni col database
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class DBTransactions {

    private final String tableTrans = "TRANSACTIONS"; //nome tabella
    private final String colTrans1 = "id";
    private final String colTrans2 = "game_id";
    private final String colTrans3 = "manche";
    private final String colTrans4 = "player";
    private final String colTrans5 = "score";
    private final String colTrans6 = "winlose";

    private final String tableCard = "CARDS c";
    private final String col1Card = "c.id";
    private final String col2Card = "c.suit";
    private final String col3Card = "c.point";

    private final String tableRelation = "TRANSACTION_CARD";
    private final String col1Rel = "transaction_id";
    private final String col2Rel = "card_id";

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
                            double win, ArrayList<Card> cardal) {
        this.id_game = id_game;
        this.manche = manche;
        this.player = player;
        this.score = score;
        this.win = win;
        this.cardAL = cardal;
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
    public void setIDT(long id) {
        this.idT = id;
    }

    /**Restituisce l'id transazione
     *
     * @return id transazione
     */
    public long getIdT() {
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
        String sql = "INSERT INTO " + tableTrans + "(" + colTrans2 + "," + colTrans3 + "," +
                    colTrans4 + "," + colTrans5 + "," + colTrans6 + ") VALUES (?,?,?,?,?)";
        PreparedStatement prpstmt = conn.prepareStatement(sql);
        Common.setParameter(prpstmt, 1, getId_game(), Types.BIGINT);
        Common.setParameter(prpstmt, 2, getManche(), Types.INTEGER);
        Common.setParameter(prpstmt, 3, getPlayer(), Types.VARCHAR);
        Common.setParameter(prpstmt, 4, getScore(), Types.DOUBLE);
        Common.setParameter(prpstmt, 5, getWin(), Types.DOUBLE);
        Logging.logInfo(prpstmt.toString());
        prpstmt.execute();
        setIDT(getLastInsertId(conn));
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
                                                IOException, Exception{
        for (int i=0; i<transactionsAL.size(); i++)
            transactionsAL.get(i).executeSingleTransaction();
        resetArraylistTansactions();
    }

    /**Scrive su db le carte presenti nell'arraylist delle transazioni
     *
     * @param conn connessione db
     * @throws java.sql.SQLException
     * @throws java.lang.Exception
     */
    private void executeArraylistCardTransaction(Connection conn) throws SQLException, Exception{
        String sql = "INSERT INTO " + tableRelation + " VALUES (?,(SELECT " + col1Card +
                    " FROM " + tableCard + " WHERE " + col2Card + "=? AND " + col3Card + "=?));";
        for (int i=0; i<cardAL.size(); i++){
            PreparedStatement prpstmt = conn.prepareStatement(sql);
            Common.setParameter(prpstmt, 1, getIdT(), Types.BIGINT);
            Common.setParameter(prpstmt, 2, cardAL.get(i).getSuit().toString(), Types.VARCHAR);
            Common.setParameter(prpstmt, 3, cardAL.get(i).getPoint().toString(), Types.VARCHAR);
            Logging.logInfo(prpstmt.toString());
            prpstmt.execute();
        }
    }

    /**Aggiunge all'arraylist una transazione da registrare a posteriori
     *
     * @param dbt transazione
     */
    public void addToArraylistTransactions(DBTransactions dbt){
        if (transactionsAL==null)
            transactionsAL = new ArrayList<DBTransactions>();
        transactionsAL.add(dbt);
    }

    /**azzera/resetta l'arraylist
     *
     */
    public void resetArraylistTansactions(){
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
        String sql = "SELECT * FROM " + tableTrans + " WHERE " + colTrans2 + "= ?;";
        PreparedStatement prpstmt = conn.prepareStatement(sql);
        Common.setParameter(prpstmt, 1, getId_game(), Types.BIGINT);
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
    private long getLastInsertId(Connection conn) throws ClassNotFoundException, SQLException, IOException{
        long last_id = -1;
        String sql = "SELECT LAST_INSERT_ID();";
        PreparedStatement prpstmt = conn.prepareStatement(sql);
        ResultSet rs = prpstmt.executeQuery();
        if (rs.next())
            last_id = rs.getLong(1);        
        return last_id;
    }
} //end class