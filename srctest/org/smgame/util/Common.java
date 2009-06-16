package org.smgame.util;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Common {
    
    final static String separ = File.separator;
    final static String curDir = "c:"+separ+"smgamelog";
    final static String dirResource = "org" + separ + "smgame" + separ + "resource" + separ;
    final static String dirResourceCard = dirResource + "cardimage" + separ;

    /**Restituisce il percorso di lavoro corrente comprensivo di primo separatore
     *
     * @return percorso lavoro
     */
    public static String getWorkspace(){
        //return curDir + separ;
        return curDir + separ;
    }

    /**Restituisce il percorso delle resource
     *
     * @return percorso resource
     */
    public static String getResource(){
        return dirResource;
    }

    /**Restituisce il percorso delle carte correnti
     *
     * @return percorso carte
     */
    public static String getResourceCards(String typecard){
        /*TODO: aggiustare il path x le carte in merito alla scelta delle carte*/
        return dirResourceCard  + typecard + separ;
    }

    /**Restituisce la cartella dei log
     *
     * @return workspace log
     */
    public static String getWorkspaceLog(){
        //String log = getTempDir() + "smgamelog";
        String log = curDir;
        File f = new File(log);
        if (!f.exists())
            f.mkdir();
        return log + separ;
    }

    /**Restituisce data e ora in formato stringa
     *
     * @return aaaammgg_hhmmss
     */
    public static String getCurrentDateTime(){
        GregorianCalendar cal = new GregorianCalendar();
        String data = cal.get(Calendar.YEAR) + (cal.get(Calendar.MONTH)+1) + cal.get(Calendar.DATE) + "_" +
                cal.get(Calendar.HOUR_OF_DAY) + cal.get(Calendar.MINUTE) + cal.get(Calendar.SECOND);
        System.out.println(data);
        return data;
    }

    /**imposta i tipi di valore da usare per la preparedStatement
     *
     * @param stmt preparedstatement
     * @param index indice
     * @param value valore
     * @param type tipo
     * @throws java.sql.SQLException
     * @throws java.lang.Exception
     */
    public static void setParameter(PreparedStatement stmt, int index,
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
        } //end
    } //end setParameter
    
}//end class