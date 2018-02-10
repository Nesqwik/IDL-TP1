package wator;

import core.misc.Environment;

/**
 * Requin qui execute qu'une action
 */
public class SharkAgentAction extends SharkAgent {


    /**
     * Constructeur du requin qui execute qu'une action
     * 
     * @param env
     * @param x
     * @param y
     * @param breedTime
     * @param feedTime
     */
    public SharkAgentAction(Environment env, Integer x, Integer y, Integer breedTime, Integer feedTime) {
        super(env, x, y, breedTime, feedTime);
    }

    @Override
    public void decide() {
        super.decide();

        boolean hasEaten = eatIfCan();
        if (!hasEaten) {
            boolean hasReproduct = reproductIfCan();
            if (!hasReproduct) {
                moveIfCan();
            }
        }

        if (feedTime == 0) {
            this.die();
        }
    }
}
