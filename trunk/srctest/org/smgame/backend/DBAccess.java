package org.smgame.backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.smgame.util.Common;
import org.smgame.util.Logging;

/** Classe DbAccess/accesso database
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class DBAccess {
    //driver mysql

    final private String DRIVER_CLASS_NAME_MYSQL = "org.gjt.mm.mysql.Driver";
    //database management system usato
    //private String DBMS;
    //
    
    //oggetto connessione
    private static Connection conn = null;

    /**Costruttore privato
     *
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    private DBAccess() throws ClassNotFoundException, SQLException, IOException {
        DBPropertiesVO dbpropVO = DBPropertiesVO.getIstance();
        //carico il driver JDBC MYSQL
        Class.forName(DRIVER_CLASS_NAME_MYSQL);
        //creo l'url JDBC per la connessione
        conn = DriverManager.getConnection(dbpropVO.getURL(), dbpropVO.getUSER(), dbpropVO.getPASSWORD());
        Logging.logInfo("Connessione effettuata con: " + dbpropVO.getURL());
    }

    /**inizializza e restituisce l'oggetto connessione
     *
     * @return connessione
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
        if ((conn == null) || (!conn.isValid(0))) {
            DBAccess dba = new DBAccess();
        }
        return conn;
    }

    /**Chiude la connessione
     *
     */
    public static void closeConnection() throws SQLException {
        conn.close();
        conn = null;
    }

    /**Verifica lo stato della connessione
     *
     * @return true se attiva, false se null
     */
    public static boolean testConnection() throws Exception {
        Connection tempConn = getConnection();
        if (tempConn.isValid(0))
            return true;       
        return false;
    }
    
}//end class
