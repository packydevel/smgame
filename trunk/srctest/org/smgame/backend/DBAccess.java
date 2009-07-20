package org.smgame.backend;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.smgame.util.ResourceLocator;
import org.smgame.util.Logging;

/** Classe DbAccess/accesso database
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class DBAccess {
    final private String DRIVER_CLASS_NAME_MYSQL = "org.gjt.mm.mysql.Driver";
    final private static String DBPROP = "database.properties";
    private static Connection conn = null;

    /**Costruttore privato
     *
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    private DBAccess() throws ClassNotFoundException, SQLException, IOException, URISyntaxException {
        DBPropertiesVO dbpropVO = requestDBPropertiesVO();
        //carico il driver JDBC MYSQL
        Class.forName(DRIVER_CLASS_NAME_MYSQL);
        //creo l'url JDBC per la connessione
        String url = dbpropVO.getUri() + dbpropVO.getServer() + ":" + dbpropVO.getPort() + "/" +
                dbpropVO.getDatabase();
        conn = DriverManager.getConnection(url, dbpropVO.getUser(), dbpropVO.getPassword());
        Logging.logInfo("Connessione effettuata con: " + url);
    }

    /**inizializza e restituisce l'oggetto connessione
     *
     * @return connessione
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException, IOException, URISyntaxException {
        if ((conn == null) || (!conn.isValid(0))) {

            @SuppressWarnings("unused")
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
    public static DBPropertiesVO requestDBPropertiesVO() throws URISyntaxException, IOException {
        Properties properties = new Properties();
        
        properties.load(new FileInputStream(new File(new URI(ResourceLocator.getResourceConfig() + DBPROP))));
        
        Logging.logInfo("Caricamento database.properties effettuato");

        DBPropertiesVO dbPropVO = new DBPropertiesVO();
        dbPropVO.setUri(properties.getProperty("URI"));
        dbPropVO.setServer(properties.getProperty("SERVER"));
        dbPropVO.setPort(properties.getProperty("PORT"));
        dbPropVO.setDatabase(properties.getProperty("DATABASE"));
        dbPropVO.setUser(properties.getProperty("USER"));
        dbPropVO.setPassword(properties.getProperty("PASSWORD"));

        return dbPropVO;
    }
    
}//end class