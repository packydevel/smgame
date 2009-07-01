package org.smgame.client.frontend;

import java.util.LinkedHashMap;

/**value objects della storyboard
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */
public class StoryBoardVO extends MainVO {
    private LinkedHashMap<Long, Object[][]> story;

    /**restituisce i dati dello storico per ogni giocatore
     *
     * @return mappa giocatore-storico
     */
    public LinkedHashMap<Long, Object[][]> getStory() {
        return story;
    }

    /**imposta lo storico
     *
     * @param story mappa giocatore storico
     */
    public void setStory(LinkedHashMap<Long, Object[][]> story) {
        this.story = story;
    }

    @Override
    public void clear(){
        super.clear();
        story = null;
    }
}