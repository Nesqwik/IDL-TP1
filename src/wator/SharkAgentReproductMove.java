package wator;

import core.misc.Environment;

public class SharkAgentReproductMove extends SharkAgent {

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
