package org.smgame.backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class DBTransactions {

    private long id_game;
    private String player;
    private double score;
    private double win;

    /**Costruttore
     *
     * @param id_game idgioco
     * @param player giocatore
     * @param score punteggio
     * @param win vincita
     */
    public DBTransactions(long id_game, String player, double score, double win) {
        this.id_game = id_game;
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

    /**aggiunge la transazione
     *
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     * @throws java.lang.Exception
     */
    public void addTransaction() throws ClassNotFoundException, SQLException, IOException, Exception{
        Connection conn = DBAccess.getConnection();
        String sql = "INSERT INTO transactions VALUES (?,?,?,?)";
        PreparedStatement prpstmt = conn.prepareStatement(sql);
        setParameter(prpstmt, 1, getId_game(), Types.INTEGER);
        setParameter(prpstmt, 2, getPlayer(), Types.VARCHAR);
        setParameter(prpstmt, 3, getScore(), Types.DOUBLE);
        setParameter(prpstmt, 4, getWin(), Types.DOUBLE);
        prpstmt.execute();
    }

    

    //imposta i tipi per la preparedStatement
    private void setParameter (PreparedStatement stmt, int index,
                                    Object value, int type) throws SQLException, Exception {
        if (value == null)
          stmt.setNull(index, type);
        else {
          if (type == Types.VARCHAR)
            stmt.setString(index, (String)value);
          else if (type == Types.INTEGER)
            stmt.setLong(index, ((Integer)value).intValue());
          else if (type == Types.DOUBLE)
            stmt.setDouble(index, ((Double)value).doubleValue());
          //else if (type == Types.TIMESTAMP)
            //stmt.setTimestamp(index, ((Timestamp) valore));
          else
            throw new Exception("Tipo di dato non gestito");
        } //end else valore == null
    } //end setParameter


}
