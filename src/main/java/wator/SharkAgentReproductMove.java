package wator;

import core.misc.Environment;

/**
 * Requin qui se reproduit en mangeant
 */
public class SharkAgentReproductMove extends SharkAgent {

    /**
     * Constructeur du requin
     * 
     * @param env
     * @param x
     * @param y
     * @param breedTime
     * @param feedTime
     */
    public SharkAgentReproductMove(Environment env, Integer x, Integer y, Integer breedTime, Integer feedTime) {
        super(env, x, y, breedTime, feedTime);
    }

    @Override
    public void decide() {
        super.decide();

        boolean hasMoved = moveIfCan();
        if (hasMoved) {
            if(this.canReproduct()) {
                reproduct(this.lastX, this.lastY);
            }
        } else {
            eatAndMoveIfCan();
        }

        if (feedTime == 0) {
            this.die();
        }
    }
}
