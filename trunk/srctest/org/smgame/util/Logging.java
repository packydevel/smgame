package org.smgame.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logging {

    private static Logger logger;
    private static FileHandler filehandler;

    public static boolean createLog(String name) {
        logger = Logger.getLogger(name);
        String dir = Common.getWorkspaceLog() + name + ".log";
        try {
            // This block configure the logger with handler and formatter
            filehandler = new FileHandler(dir, true);
            logger.addHandler(filehandler);
            logger.setLevel(Level.ALL);
            SimpleFormatter formatter = new SimpleFormatter();
            filehandler.setFormatter(formatter);
        } catch (IOException ioe) {
            logger.severe(ioe.getLocalizedMessage());
            return false;
        } catch (SecurityException se) {
            logger.severe(se.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public static void logExceptionSevere(Exception e){
        logger.severe(e.getMessage());
    }

    public static void logExceptionWarning(Exception e){
        logger.warning(e.getMessage());
    }

    public static void logInfo(String msg){
        logger.info(msg);
    }


}