package wator;

import core.agents.Agent;
import core.misc.Environment;

public class SharkAgentReproductEat extends SharkAgent {

    public SharkAgentReproductEat(Environment env, Integer x, Integer y, Integer breedTime, Integer feedTime) {
        super(env, x, y, breedTime, feedTime);
    }

    @Override
    public void decide() {
        super.decide();
        Agent[][] moore = environment.getMoore(this);


        boolean hasMoved = eatAndMoveIfCan(moore);
        if (hasMoved) {
            if(this.canReproduct()) {
                reproduct(this.lastX, this.lastY);
            }
        } else {
            moveIfCan(moore);
        }

        if (feedTime == 0) {
            this.die();
        }
    }
}
