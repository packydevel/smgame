package org.smgame.util;

import java.io.File;
import java.net.URL;

/**Classe metodi comuni
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class ResourceLocator {

    static String separ = File.separator;
    static String curDir;
    final static String dirResource = "/org/smgame/resource/";
    final static String dirResourceCard = dirResource + "cardimage/";
    final static String dirResourceAuthor = dirResource + "authorimage/";

    /**Restituisce il percorso di lavoro corrente comprensivo di primo separatore
     *
     * @return percorso lavoro
     */
    public static String getWorkspace() {
        return curDir;
    }

    /**imposta il percorso di lavoro
     *
     * @param dir directory
     * @param applet true = esecuzione da applet
     */
    public static void setWorkspace(String dir, boolean applet) {
        if (!applet) {
            String prefix = "file:";
            if (isWindows()) {
                prefix = prefix + "/";
            }
            String tempDir = prefix + dir + separ;
            curDir = tempDir.replace('\\', '/');
        } else
            curDir = dir;
        //System.out.println(curDir);
    }

    /**Restituisce il percorso delle resource
     *
     * @return percorso resource
     */
    public static String getResource() {
        return dirResource;
    }

    /**Restituisce il percorso delle carte correnti
     *
     * @param typecard tipo mazzo usato
     * @return percorso carte
     */
    public static String getResourceCards(String typecard) {
        return dirResourceCard + typecard + "/";
    }

    /**Restituisce il percorso delle immagini degli Autori del Gioco
     *
     * @return percorso img
     */
    public static String getResourceAuthors() {
        return dirResourceAuthor;
    }

    /**Converte un percorso stringa in una URL
     *
     * @param path percorso
     * @return url
     */
    public static URL convertStringToURL(String path) {
        return ResourceLocator.class.getResource(path);
    }

    public static boolean isWindows() {
        boolean windows = false;
        String osName = System.getProperty("os.name").toLowerCase();

        if (!osName.equals("linux")) {
            if ((osName.length() > 6) &&
                    (osName.substring(0, 7).toLowerCase().equalsIgnoreCase("windows"))) {
                windows = true;
            }
        }

        return windows;
    }
}//end class

