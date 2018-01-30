package wator;

import core.agents.Agent;
import core.misc.Config;
import core.misc.Environment;
import core.misc.Logger;
import core.misc.SMA;

public class SharkAgentClassic extends SharkAgent {

    public SharkAgentClassic(Environment env, Integer x, Integer y, Integer breedTime, Integer feedTime) {
        super(env, x, y, breedTime, feedTime);
    }

    @Override
    public void decide() {
        super.decide();
        Agent[][] moore = environment.getMoore(this);

        boolean hasEatenAndMoved = eatAndMoveIfCan(moore);
        if (hasEatenAndMoved) {
            if (this.canReproduct()) {
                reproduct(this.lastX, this.lastY);
            }
        } else {
            boolean hasMoved = moveIfCan(moore);
            if (hasMoved && this.canReproduct()) {
                reproduct(this.lastX, this.lastY);
            }
        }

        if (feedTime == 0) {
            this.die();
        }
    }
}
