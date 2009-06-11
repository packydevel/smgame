/** Classe DbAccess/accesso database
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
package org.smgame.backend;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.smgame.util.Common;

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
    private static Connection conn;

    private DBAccess() throws ClassNotFoundException, SQLException, IOException {
        conn = null;
        readProperties();
        //carico il driver JDBC MYSQL
        Class.forName(DRIVER_CLASS_NAME_MYSQL);
        //creo l'url JDBC per la connessione
        String url = URI + SERVER + ":" + PORT + "/" + DATABASE;
        System.out.println(url);
        conn = DriverManager.getConnection(url, USER, PASSWORD);
    }

    /**inizializza la connessione
     *
     */
    public static void initConnection() throws ClassNotFoundException, SQLException, IOException {
        DBAccess dba = new DBAccess();
    }

    /**Restituisce l'oggetto connessione
     *
     * @return connessione
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
        if (conn == null) {
            initConnection();
        }
        return conn;
    }

    /**Chiude la connessione
     *
     */
    public static void closeConnection() throws SQLException {
        conn.close();
    }
    //legge il file contenente le informazioni sul database
    private void readProperties() throws IOException {
        Properties properties = new Properties();
        //directory di lavoro
        String file = Common.getWorkspace() + "database.properties";
        //caricamento del file properties
        properties.load(new FileInputStream(file));
        //DBMS = properties.getProperty("DBMS");
        URI = properties.getProperty("URI");
        SERVER = properties.getProperty("SERVER");
        PORT = properties.getProperty("PORT");
        DATABASE = properties.getProperty("DATABASE");
        USER = properties.getProperty("USER");
        PASSWORD = properties.getProperty("PASSWORD");
    }
}//end class
