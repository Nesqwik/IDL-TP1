package wator;

import core.misc.Environment;

/**
 * Requin qui mange et bouge s'il peut, et vérifie s'il peut se reproduire
 * Sinon il bouge s'il peut et vérifie s'il peut se reproduire
 */
public class SharkAgentClassic extends SharkAgent {

    /**
     * Constructeur du requin
     * 
     * @param env
     * @param x
     * @param y
     * @param breedTime
     * @param feedTime
     */
    public SharkAgentClassic(Environment env, Integer x, Integer y, Integer breedTime, Integer feedTime) {
        super(env, x, y, breedTime, feedTime);
    }

    @Override
    public void decide() {
        super.decide();

        boolean hasEatenAndMoved = eatAndMoveIfCan();
        if (hasEatenAndMoved) {
            if (this.canReproduct()) {
                reproduct(this.lastX, this.lastY);
            }
        } else {
            boolean hasMoved = moveIfCan();
            if (hasMoved && this.canReproduct()) {
                reproduct(this.lastX, this.lastY);
            }
        }

        if (feedTime == 0) {
            this.die();
        }
    }
}
