package wator;

import core.agents.Agent;
import core.misc.Environment;

public class SharkAgentReproductMove extends SharkAgent {

    public SharkAgentReproductMove(Environment env, Integer x, Integer y, Integer breedTime, Integer feedTime) {
        super(env, x, y, breedTime, feedTime);
    }

    @Override
    public void decide() {
        super.decide();
        Agent[][] moore = environment.getMoore(this);


        boolean hasMoved = moveIfCan(moore);
        if (hasMoved && this.canReproduct()) {
            reproduct(this.lastX, this.lastY);
        } else {
            eatAndMoveIfCan(moore);
        }

        if (feedTime == 0) {
            this.die();
        }
    }
}
