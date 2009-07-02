package org.smgame.beckend;

/**Classe proprietà database ValueObject
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class DBPropertiesVO {

    private String uri, server, database, port, user, password;

    /**Restituisce il database
     *
     * @return database
     */
    public String getDatabase() {
        return database;
    }

    /**imposta il database
     *
     * @param database db
     */
    public void setDatabase(String database) {
        this.database = database;
    }

    /**restituisce la password
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**imposta la password
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**restituisce la porta
     *
     * @return porta
     */
    public String getPort() {
        return port;
    }

    /**imposta la porta
     *
     * @param port porta
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**Restituisce il server
     *
     * @return server
     */
    public String getServer() {
        return server;
    }

    /**imposta il server
     *
     * @param server
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**Restituisce l'uri
     *
     * @return uri
     */
    public String getUri() {
        return uri;
    }

    /**imposta l'uri
     *
     * @param uri uri
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**Restituisce l'utente
     *
     * @return utente
     */
    public String getUser() {
        return user;
    }

    /**imposta l'utente
     *
     * @param user utente
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**azzera i ValueOjbects
     *
     */
    public void clear(){
        server = null;
        user = null;
        uri = null;
        database = null;
        port = null;
        password = null;
    }
}