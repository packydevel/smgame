package org.smgame.backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import org.smgame.util.Common;
import org.smgame.util.Logging;

/** Classe DBTransactions/gestisce le transazioni col database
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class DBTransactions {

    private final String table = "TRANSACTIONS"; //nome tabella
    private final String col2 = "game_id";
    private final String col3 = "manche";
    private final String col4 = "player";
    private final String col5 = "score";
    private final String col6 = "winlose";

    private long id_game; //id partita
    private int manche; //numero manche
    private String player; //nome giocatore
    private double score; //punteggio
    private double win; //vincita

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
    public DBTransactions(long id_game, int manche, String player, double score, double win) {
        this.id_game = id_game;
        this.manche = manche;
        this.player = player;
        this.score = score;
        this.win = win;
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

    /**imposta la manche
     *
     * @param manche numero di manche
     */
    public void setManche(int manche) {
        this.manche = manche;
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
        String sql = "INSERT INTO " + table + "(" + col2 + "," + col3 + "," + col4 + "," + col5 + "," + col6 +
                ") VALUES (?,?,?,?,?)";
        PreparedStatement prpstmt = conn.prepareStatement(sql);
        Common.setParameter(prpstmt, 1, getId_game(), Types.BIGINT);
        Common.setParameter(prpstmt, 2, getManche(), Types.INTEGER);
        Common.setParameter(prpstmt, 3, getPlayer(), Types.VARCHAR);
        Common.setParameter(prpstmt, 4, getScore(), Types.DOUBLE);
        Common.setParameter(prpstmt, 5, getWin(), Types.DOUBLE);
        Logging.logInfo(prpstmt.toString());
        prpstmt.execute();
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
        String sql = "SELECT * FROM " + table + " WHERE " + col2 + "= ?;";
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

} //end class