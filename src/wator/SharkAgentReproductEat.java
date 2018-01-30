package wator;

import core.misc.Environment;

public class SharkAgentReproductEat extends SharkAgent {

    public SharkAgentReproductEat(Environment env, Integer x, Integer y, Integer breedTime, Integer feedTime) {
        super(env, x, y, breedTime, feedTime);
    }

    @Override
    public void decide() {
        super.decide();

        boolean hasMoved = eatAndMoveIfCan();
        if (hasMoved) {
            if(this.canReproduct()) {
                reproduct(this.lastX, this.lastY);
            }
        } else {
            moveIfCan();
        }

        if (feedTime == 0) {
            this.die();
        }
    }
}
