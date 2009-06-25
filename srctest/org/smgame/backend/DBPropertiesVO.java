package org.smgame.backend;

import java.io.IOException;
import java.util.Properties;
import org.smgame.util.Common;
import org.smgame.util.Logging;

/**
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class DBPropertiesVO {

    private String URI, SERVER, DATABASE, PORT, USER, PASSWORD, URL;
    private static DBPropertiesVO dbPropVO;

    private DBPropertiesVO() throws IOException{
        readProperties();
    }

    public static DBPropertiesVO getIstance() throws IOException{
        if (dbPropVO==null)
            dbPropVO = new DBPropertiesVO();
        return dbPropVO;
    }

    /**legge il file contenente le informazioni sul database e setta le variabili per la connessione
     *
     * @throws java.io.IOException
     */
    private void readProperties() throws IOException{
        Properties properties = new Properties();

        properties.load(getClass().getResourceAsStream(Common.getResource()+"database.properties"));

        Logging.logInfo("Caricamento database.properties effettuato");

        URI = properties.getProperty("URI");
        SERVER = properties.getProperty("SERVER");
        PORT = properties.getProperty("PORT");
        DATABASE = properties.getProperty("DATABASE");
        USER = properties.getProperty("USER");
        PASSWORD = properties.getProperty("PASSWORD");
        URL = URI + SERVER + ":" + PORT + "/" + DATABASE;
    }

    public String getDATABASE() {
        return DATABASE;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public String getPORT() {
        return PORT;
    }

    public String getSERVER() {
        return SERVER;
    }

    public String getURI() {
        return URI;
    }

    public String getUSER() {
        return USER;
    }

    public String getURL() {
        return URL;
    }
}