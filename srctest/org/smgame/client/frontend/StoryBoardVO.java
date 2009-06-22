package org.smgame.client.frontend;

import java.util.LinkedHashMap;

/**
 *
 * @author Traetta  Pasquale 450428
 * @author Mignogna Luca     467644
 */

public class StoryBoardVO extends MainVO {
    private LinkedHashMap<Long, Object[][]> story;

    public LinkedHashMap<Long, Object[][]> getStory() {
        return story;
    }

    public void setStory(LinkedHashMap<Long, Object[][]> story) {
        this.story = story;
    }

    @Override
    public void clear(){
        super.clear();
        story = null;
    }
}