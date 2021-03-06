package org.smgame.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**Classe che gestisce la creazione e scrittura degli eventi in file di log
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class Logging {

    private static Logger logger;
    private static FileHandler filehandler;

    /**Crea un file di logging col nome passato per parametro
     *
     * @param name nome logging
     * @return true = successo
     */
    public static boolean createLog(String name) {
        logger = Logger.getLogger(name);
        String dir = ResourceLocator.getWorkspace() + name + ".log";

        try {
            File f = new File(new java.net.URI(dir));
            // This block configure the logger with handler and formatter
            filehandler = new FileHandler(f.getAbsolutePath(), true);
            logger.addHandler(filehandler);
            logger.setLevel(Level.ALL);
            SimpleFormatter formatter = new SimpleFormatter();
            filehandler.setFormatter(formatter);
        } catch (URISyntaxException urise) {
            logger.severe(urise.getLocalizedMessage());
            return false;
        } catch (IOException ioe) {
            logger.severe(ioe.getLocalizedMessage());
            return false;
        } catch (SecurityException se) {
            logger.severe(se.getLocalizedMessage());
            return false;
        }
        return true;
    }

    /**Scrive nel log il tipo di errore grave
     *
     * @param e eccezione Exception
     */
    @SuppressWarnings("unchecked")
    public static void logExceptionSevere(Class c, Exception e){
        logger.severe(c.getName() + " - " + e.toString());
    }

    /**Scrive nel log il tipo di avviso
     *
     * @param boe eccezione BetOverflowException
     */
    public static void logExceptionWarning(BetOverflowException boe){
        StackTraceElement[] sTrace = boe.getStackTrace();
        String message = sTrace[0].getClassName() + " - " +
            sTrace[0].getMethodName() + "\n" + boe.getMessage();
        logger.severe(message);
    }

    /**Scrive nel log il tipo di avviso
     *
     * @param soe eccezione ScoreOverflowException
     */
    public static void logExceptionWarning(ScoreOverflowException soe){
        StackTraceElement[] sTrace = soe.getStackTrace();
        String message = sTrace[0].getClassName() + " - " +
            sTrace[0].getMethodName() + "\n" + soe.getMessage();
        logger.severe(message);
    }

    /**Scrive nel log un messaggio contenente informazione
     *
     * @param msg messaggio
     */
    public static void logInfo(String msg){
        logger.info(msg);
    }
}