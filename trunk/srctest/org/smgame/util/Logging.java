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
        e.printStackTrace();
        logger.severe(e.toString());
        
    }

    public static void logExceptionWarning(BetOverflowException boe){
        StackTraceElement[] sTrace = boe.getStackTrace();
        String message = sTrace[0].getClassName() + " - " +
            sTrace[0].getMethodName() + "\n" + boe.getMessage();
        logger.severe(message);
    }

    public static void logExceptionWarning(ScoreOverflowException soe){
        StackTraceElement[] sTrace = soe.getStackTrace();
        String message = sTrace[0].getClassName() + " - " +
            sTrace[0].getMethodName() + "\n" + soe.getMessage();
        logger.severe(message);
    }

    public static void logInfo(String msg){
        logger.info(msg);
    }
}