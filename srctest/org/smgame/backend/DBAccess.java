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
    private String URI;
    //identificativo server
    private String SERVER;
    //nome basedati
    private String DATABASE;
    //porta
    private String PORT;
    //username accesso db
    private String USER;
    //password per l'user
    private String PASSWORD;
    //oggetto connessione
    private static Connection conn = null;

    /**Costruttore privato
     *
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    private DBAccess() throws ClassNotFoundException, SQLException, IOException {
        readProperties();
        //carico il driver JDBC MYSQL
        Class.forName(DRIVER_CLASS_NAME_MYSQL);
        //creo l'url JDBC per la connessione
        String url = URI + SERVER + ":" + PORT + "/" + DATABASE;
        conn = DriverManager.getConnection(url, USER, PASSWORD);
        Logging.logInfo("Connessione effettuata con: " + url);
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

    /**legge il file contenente le informazioni sul database e setta le variabili per la connessione
     *
     * @throws java.io.IOException
     */
    private void readProperties() throws IOException {
        Properties properties = new Properties();

        properties.load(getClass().getResourceAsStream(Common.getResource()+"database.properties"));
        
        Logging.logInfo("Caricamento database.properties effettuato");
        //DBMS = properties.getProperty("DBMS");
        URI = properties.getProperty("URI");
        SERVER = properties.getProperty("SERVER");
        PORT = properties.getProperty("PORT");
        DATABASE = properties.getProperty("DATABASE");
        USER = properties.getProperty("USER");
        PASSWORD = properties.getProperty("PASSWORD");
    }
}//end class
