package org.smgame.backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

public class DBTransactions {

    private final String table = " transactions ";
    private long id_game;
    private int manche;
    private String player;
    private double score;
    private double win;

    /**Costruttore
     * 
     * @param id_game idpartita
     */
    public DBTransactions(long id_game) {
        this.id_game = id_game;
    }

    /**Costruttore
     *
     * @param id_game idgioco
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

    public int getManche() {
        return manche;
    }

    public void setManche(int manche) {
        this.manche = manche;
    }

    /**aggiunge la transazione
     *
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     * @throws java.lang.Exception
     */
    public void addTransaction() throws ClassNotFoundException, SQLException, IOException, Exception {
        Connection conn = DBAccess.getConnection();
        String sql = "INSERT INTO" + table + "VALUES (?,?,?,?,?)";
        PreparedStatement prpstmt = conn.prepareStatement(sql);
        setParameter(prpstmt, 1, getId_game(), Types.BIGINT);
        setParameter(prpstmt, 2, getManche(), Types.INTEGER);
        setParameter(prpstmt, 3, getPlayer(), Types.VARCHAR);
        setParameter(prpstmt, 4, getScore(), Types.DOUBLE);
        setParameter(prpstmt, 5, getWin(), Types.DOUBLE);
        prpstmt.execute();
    }

    /**Restituisce l'arraylist delle transazioni
     *
     * @return arraylist transazioni
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     * @throws java.lang.Exception
     */
    public ArrayList<DBTransactions> getTransactions() throws
            ClassNotFoundException, SQLException, IOException, Exception {

        ArrayList<DBTransactions> dbtransactionsAL = new ArrayList<DBTransactions>();
        Connection conn = DBAccess.getConnection();
        String sql = "SELECT * FROM" + table + "WHERE game_id = ?";
        PreparedStatement prpstmt = conn.prepareStatement(sql);
        setParameter(prpstmt, 1, getId_game(), Types.BIGINT);

        ResultSet rs = prpstmt.executeQuery();
        while (rs.next()) {
            DBTransactions dbt = new DBTransactions(id_game, rs.getInt(2), rs.getString(3),
                    rs.getDouble(4), rs.getDouble(5));
            dbtransactionsAL.add(dbt);
        }
        return dbtransactionsAL;
    }

    //imposta i tipi per la preparedStatement
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
        } //end else valore == null
    } //end setParameter

} //end class